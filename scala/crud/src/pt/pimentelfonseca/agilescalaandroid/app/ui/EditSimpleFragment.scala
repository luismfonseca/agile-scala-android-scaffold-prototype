package pt.pimentelfonseca.agilescalaandroid.app.ui

import android.app.ActionBar
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

import org.scaloid.common._

import com.google.gson.Gson

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.text.format.DateFormat
import java.util.Date
import java.util.Calendar

import pt.pimentelfonseca.agilescalaandroid.app.models.Simple
import pt.pimentelfonseca.agilescalaandroid.app.R

object EditSimpleFragment {
  val BUNDLE_MODEL_JSON: String = "model_json"
  val BUNDLE_CREATE_NEW: String = "create_new"

  def newInstance(model: Simple): EditSimpleFragment = {
    val arguments = new Bundle()
    arguments.putString(BUNDLE_MODEL_JSON, new Gson().toJson(model))

    val fragment = new EditSimpleFragment()
    fragment.setArguments(arguments)
    fragment
  }
}

class EditSimpleFragment extends Fragment {

  var mModel: Simple = _
  var mDateAsFirstButton: Button = _
  var mStringAsSecond: TextView = _

  private val mActionBarListener = (view: View) => {
    view.getId() match {
      case R.id.action_cancel => {
        getActivity().setResult(EditSimpleActivity.RESULT_NOTHING_CHANGED)
        getActivity().finish()
      }
      case R.id.action_done => {
        val finalSimple = new Simple(
          mModel.dateAsFirst,
          mStringAsSecond.getText().toString()
		)

        val data = new Intent()
        data.putExtra(EditSimpleFragment.BUNDLE_MODEL_JSON, new Gson().toJson(finalSimple))

        getActivity().setResult(EditSimpleActivity.RESULT_EDIT_OCCURRED, data)
        getActivity().finish()
      }
    }
  }

  override def onCreate(bundle: Bundle): Unit = {
    super.onCreate(bundle)

    if (getArguments() != null) {
      if (getArguments().getBoolean(EditSimpleFragment.BUNDLE_CREATE_NEW))
      {
        mModel = new Simple(
          new Date(),
          "Lorem ipsum dolor sit amet."
        )
      }
      else
      {
        val json = getArguments().getString(EditSimpleFragment.BUNDLE_MODEL_JSON)
        mModel = new Gson().fromJson(json, classOf[Simple])
      }
    }
  }

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val view = inflater.inflate(R.layout.fragment_edit_simple, container, false)

    val actionBarButtons = inflater.inflate(R.layout.actionbar_edit_cancel_done, new LinearLayout(getActivity()), false)


    val cancelActionView = actionBarButtons.findViewById(R.id.action_cancel)
    cancelActionView.setOnClickListener(mActionBarListener)

    val doneActionView = actionBarButtons.findViewById(R.id.action_done)
    doneActionView.setOnClickListener(mActionBarListener)

    getActivity().getActionBar().setCustomView(actionBarButtons)
    getActivity().getActionBar().setDisplayOptions(
        ActionBar.DISPLAY_SHOW_CUSTOM,
        ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM)

    mDateAsFirstButton = view.findViewById(R.id.create_simple_date_as_first).asInstanceOf[Button]
    mDateAsFirstButton.onClick({
      
        val calendar = Calendar.getInstance()
		if (mModel.dateAsFirst != null) {
          calendar.setTime(mModel.dateAsFirst)
		}
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

          override def onDateSet(datePickerView: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int): Unit = {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            mModel = mModel.copy(dateAsFirst = calendar.getTime())
            mDateAsFirstButton.setText(DateFormat.format("dd-MM-yyyy", mModel.dateAsFirst))
          }
        }, year, month, day).show()

    })
    mStringAsSecond = view.findViewById(R.id.create_simple_string_as_second).asInstanceOf[TextView]

    if (mModel != null)
    {
      mDateAsFirstButton.setText(DateFormat.format("dd-MM-yyyy", mModel.dateAsFirst))
      mStringAsSecond.setText(mModel.stringAsSecond)

    }

    return view
  }

  override def onDestroyView(): Unit = {
    super.onDestroyView()

    getActivity().getActionBar().setCustomView(null)
  }

}
