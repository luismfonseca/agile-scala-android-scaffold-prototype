package pt.pimentelfonseca.agilescalaandroid.app.ui

import android.app.ListFragment
import android.os.Bundle
import android.view.View
import android.widget.ListView

import org.scaloid.common._

import com.google.gson.Gson

import android.text.format.DateFormat
import java.util.Date

import pt.pimentelfonseca.agilescalaandroid.app.models.Simple

object SimpleListFragment {
  val BUNDLE_MODEL_JSON = "model_json"

  val MENU_ITEM_EDIT = 1
  val MENU_ITEM_DELETE = 2

  val REQUEST_EDIT = 1

  def newInstance(model: Simple): SimpleListFragment = {
    val arguments = new Bundle()
    arguments.putString(BUNDLE_MODEL_JSON, new Gson().toJson(model))

    val fragment = new SimpleListFragment()
    fragment.setArguments(arguments)
    fragment
  }
}

class SimpleListFragment extends ListFragment {

  var mListAdapter: SimpleListAdapter = _

  lazy val mItems: Array[Simple] = {
  
    // TODO: Load real object from database
    (1 to 4).foldLeft(Array[Simple]()) {
      (acc, index) => {
        acc :+ Simple(
          new Date(),
          "Lorem ipsum dolor sit amet."
        )
      }
    }
  }
  
  override def onActivityCreated(bundle: Bundle): Unit = {
    super.onActivityCreated(bundle)

    getListView().setDividerHeight(0)

    mListAdapter = new SimpleListAdapter(getActivity(), mItems)
    setListAdapter(mListAdapter)
  }

  override def onListItemClick(listView: ListView, view: View, position: Int, id: Long) {

    val simpleFragment = SimpleFragment.newInstance(mItems(position))

    (getActivity().asInstanceOf[ChangeToFragmentHandler]).onChangeToFragment(simpleFragment)
  }
}
