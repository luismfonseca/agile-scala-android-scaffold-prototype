package pt.pimentelfonseca.agilescalaandroid.app.ui

import android.os.Bundle

import org.scaloid.common._

import pt.pimentelfonseca.agilescalaandroid.app.R

object EditSimpleActivity {
  val RESULT_EDIT_OCCURRED: Int = 1
  val RESULT_NOTHING_CHANGED: Int = 2
}

class EditSimpleActivity extends SActivity {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_edit_simple)
    if (savedInstanceState == null) {

      val fragment = new EditSimpleFragment()
      fragment.setArguments(getIntent().getExtras())

      getFragmentManager().beginTransaction()
        .add(R.id.edit_simple_container, fragment)
        .commit()
    }
  }

}
