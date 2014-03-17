package pt.pimentelfonseca.agilescalaandroid.app.ui

import android.app.AlertDialog
import android.app.Fragment
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.google.gson.Gson

import java.util.ArrayList
import java.util.Date
import java.util.List

import pt.pimentelfonseca.agilescalaandroid.app.models.Post
import pt.pimentelfonseca.agilescalaandroid.app.R

object PostFragment {
  val BUNDLE_MODEL_JSON = "model_json"

  val MENU_ITEM_EDIT = 1
  val MENU_ITEM_DELETE = 2

  val REQUEST_EDIT = 1

  def newInstance(model: Post): PostFragment = {
    val arguments = new Bundle()
    arguments.putString(BUNDLE_MODEL_JSON, new Gson().toJson(model))

    val fragment = new PostFragment()
    fragment.setArguments(arguments)
    fragment
  }

  trait PostDeleteHandler {
    def onPostDeleteHandler: Unit
  }

}

class PostFragment extends Fragment {

  var mModel: Post = _

  var mModelTitle: TextView = _
  var mModelNumberOfLikes: TextView = _
  var mModelDate: TextView = _

  override def onCreate(bundle: Bundle): Unit = {
    super.onCreate(bundle)

    if (getArguments() != null) {
        val json = getArguments().getString(PostFragment.BUNDLE_MODEL_JSON)

        mModel = new Gson().fromJson(json, classOf[Post])
    }
    else {
      mModel = new Post("", 0, new Date())
    }

    setHasOptionsMenu(true)
  }

  override def onSaveInstanceState(outState: Bundle): Unit = {
    outState.putString(PostFragment.BUNDLE_MODEL_JSON, "") //new Gson().toJson(mModel))
  }

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val view = inflater.inflate(R.layout.fragment_post, container, false)

    mModelTitle = view.findViewById(R.id.post_title).asInstanceOf[TextView]
    mModelNumberOfLikes = view.findViewById(R.id.post_number_of_likes).asInstanceOf[TextView]
    mModelDate = view.findViewById(R.id.post_date).asInstanceOf[TextView]

    displayPost()
    return view
  }

  private def displayPost(): Unit = {
    mModelTitle.setText(mModel.title);
    mModelNumberOfLikes.setText("" + mModel.numberOfLikes);
    mModelDate.setText(DateFormat.format("dd-MM-yyyy", mModel.date));
  }

  override def onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater): Unit = {
    val editMenu = menu.add(Menu.NONE, PostFragment.MENU_ITEM_EDIT, Menu.NONE, "Edit")
    editMenu.setIcon(android.R.drawable.ic_menu_edit)
    editMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

    val deleteMenu = menu.add(Menu.NONE, PostFragment.MENU_ITEM_DELETE, Menu.NONE, "Delete")
    deleteMenu.setIcon(android.R.drawable.ic_menu_delete)
    deleteMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
  }


  override def onOptionsItemSelected(item: MenuItem): Boolean = {

    item.getItemId() match {
      case PostFragment.MENU_ITEM_EDIT => {

        val intent = new Intent(getActivity(), classOf[EditPostActivity])

        val postInJson = new Gson().toJson(mModel)
        intent.putExtra(EditPostFragment.BUNDLE_MODEL_JSON, postInJson)
        startActivityForResult(intent, PostFragment.REQUEST_EDIT)

        true
      }
      case PostFragment.MENU_ITEM_DELETE => {

        val builder = new AlertDialog.Builder(getActivity())
        builder.setMessage("Are you sure you want to delete this Post?")
          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
              def onClick(dialog: DialogInterface, id: Int): Unit = {
                  Toast.makeText(PostFragment.this.getActivity(), "ok", Toast.LENGTH_LONG).show()

                  // TODO: Actually remove the object from database

                  getActivity().asInstanceOf[PostFragment.PostDeleteHandler].onPostDeleteHandler
              }
          })
          .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
              def onClick(dialog: DialogInterface, id: Int): Unit = {
              }
          })
        builder.create().show()
        true
      }
      case _ => super.onOptionsItemSelected(item)
    }
  }

  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent): Unit = {

    requestCode match {

      case PostFragment.REQUEST_EDIT => {
        if (resultCode == EditPostActivity.RESULT_EDIT_OCCURRED)
        {
            val json = data.getExtras().getString(EditPostFragment.BUNDLE_MODEL_JSON)
            mModel = new Gson().fromJson(json, classOf[Post])

            // TODO: Save the edited object to the database
            displayPost()
        }
        else
        {
            Toast.makeText(getActivity(), "Post edit was canceled.", Toast.LENGTH_LONG).show()
        }
       }
    }
  }

}
