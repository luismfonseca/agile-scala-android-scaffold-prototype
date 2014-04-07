package pt.pimentelfonseca.agilescalaandroid.app.ui

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import org.scaloid.common._

import com.google.gson.Gson

import android.text.format.DateFormat
import java.util.Date

import pt.pimentelfonseca.agilescalaandroid.app.models.Simple

import pt.pimentelfonseca.agilescalaandroid.app.R

object SimpleFragment {
  val BUNDLE_MODEL_JSON = "model_json"

  val MENU_ITEM_EDIT = 1
  val MENU_ITEM_DELETE = 2

  val REQUEST_EDIT = 1

  def newInstance(model: Simple): SimpleFragment = {
    val arguments = new Bundle()
    arguments.putString(BUNDLE_MODEL_JSON, new Gson().toJson(model))

    val fragment = new SimpleFragment()
    fragment.setArguments(arguments)
    fragment
  }

  trait SimpleDeleteHandler {
    def onSimpleDeleteHandler: Unit
  }

}

class SimpleFragment extends Fragment {

  var mModel: Simple = _
  var mModelDateAsFirst: TextView = _
  var mModelStringAsSecond: TextView = _

  override def onCreate(bundle: Bundle): Unit = {
    super.onCreate(bundle)

    if (getArguments() != null) {
        val json = getArguments().getString(SimpleFragment.BUNDLE_MODEL_JSON)

        mModel = new Gson().fromJson(json, classOf[Simple])
    }
    else {
	  throw new RuntimeException("Arguments bundle not were not included in the fragment!")
	  
	  // If you want, you can implement a default view.
      //mModel = new Simple(/* use model constructor here */)
    }

    setHasOptionsMenu(true)
  }

  override def onSaveInstanceState(outState: Bundle): Unit = {
    outState.putString(SimpleFragment.BUNDLE_MODEL_JSON, new Gson().toJson(mModel))
  }

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val view = inflater.inflate(R.layout.fragment_simple, container, false)

    mModelDateAsFirst = view.findViewById(R.id.simple_date_as_first).asInstanceOf[TextView]
    mModelStringAsSecond = view.findViewById(R.id.simple_string_as_second).asInstanceOf[TextView]

    display()
    return view
  }

  private def display(): Unit = {
    mModelDateAsFirst.setText(DateFormat.format("dd-MM-yyyy", mModel.dateAsFirst))
    mModelStringAsSecond.setText(mModel.stringAsSecond)

  }

  override def onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater): Unit = {
    val editMenu = menu.add(Menu.NONE, SimpleFragment.MENU_ITEM_EDIT, Menu.NONE, "Edit")
    editMenu.setIcon(android.R.drawable.ic_menu_edit)
    editMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

    val deleteMenu = menu.add(Menu.NONE, SimpleFragment.MENU_ITEM_DELETE, Menu.NONE, "Delete")
    deleteMenu.setIcon(android.R.drawable.ic_menu_delete)
    deleteMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {

    item.getItemId() match {
      case SimpleFragment.MENU_ITEM_EDIT => {

        val intent = new Intent(getActivity(), classOf[EditSimpleActivity])

        val json = new Gson().toJson(mModel)
        intent.putExtra(EditSimpleFragment.BUNDLE_MODEL_JSON, json)
        startActivityForResult(intent, SimpleFragment.REQUEST_EDIT)

        true
      }
      case SimpleFragment.MENU_ITEM_DELETE => {

        new AlertDialogBuilder("Delete Simple", "Do you really want to delete this Simple?")(getActivity()) {
          positiveButton(android.R.string.yes, (_, _) => {

            // TODO: Actually remove the object from database
            toast("The Simple was deleted.")

            getActivity().asInstanceOf[SimpleFragment.SimpleDeleteHandler].onSimpleDeleteHandler
          })
          negativeButton(android.R.string.cancel)
        }.show()
        true
      }
      case _ => super.onOptionsItemSelected(item)
    }
  }

  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent): Unit = {

    requestCode match {

      case SimpleFragment.REQUEST_EDIT => {
        if (resultCode == EditSimpleActivity.RESULT_EDIT_OCCURRED)
        {
            val json = data.getExtras().getString(EditSimpleFragment.BUNDLE_MODEL_JSON)
            mModel = new Gson().fromJson(json, classOf[Simple])

            // TODO: Save the edited object to the database
            display()
        }
        else
        {
            Toast.makeText(getActivity(), "Edit was canceled.", Toast.LENGTH_LONG).show()
        }
       }
    }
  }

}
