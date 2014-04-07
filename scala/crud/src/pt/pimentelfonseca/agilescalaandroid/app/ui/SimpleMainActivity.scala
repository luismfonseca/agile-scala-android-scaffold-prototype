package pt.pimentelfonseca.agilescalaandroid.app.ui

import android.app.Fragment
import android.app.FragmentManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import org.scaloid.common._

import pt.pimentelfonseca.agilescalaandroid.app.R

class SimpleMainActivity
  extends SActivity
  with SimpleFragment.SimpleDeleteHandler
  with ChangeToFragmentHandler
  with FragmentManager.OnBackStackChangedListener {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple)

    if (savedInstanceState == null) {
        getFragmentManager()
          .beginTransaction()
          .add(R.id.simple_main_container, new SimpleListFragment())
          .commit()
    }
    getFragmentManager().addOnBackStackChangedListener(this)
  }
  
  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    val currentFragment = getFragmentManager().findFragmentById(R.id.simple_main_container)

    if (currentFragment.getClass() == classOf[SimpleListFragment])
    {
        getMenuInflater().inflate(R.menu.main_simple, menu)
        return true
    }
    return super.onCreateOptionsMenu(menu)
  }

  override def onOptionsItemSelected(menuItem: MenuItem): Boolean = {
    menuItem.getItemId() match {
      case android.R.id.home => {
        getFragmentManager().popBackStack()

        true
      }

      case R.id.menu_main_new_simple => {

        val intent = new Intent(this, classOf[EditSimpleActivity])
        intent.putExtra(EditSimpleFragment.BUNDLE_CREATE_NEW, true)

        startActivity(intent)
        true
      }
      case _ => super.onOptionsItemSelected(menuItem)
    }
  }


  override def onSimpleDeleteHandler(): Unit = {
    getFragmentManager().popBackStack()
  }

  override def onChangeToFragment(fragment: Fragment): Unit = {
    getFragmentManager().beginTransaction()
      .setCustomAnimations(R.animator.slide_in, R.animator.slide_out)
      .replace(R.id.simple_main_container, fragment)
      .addToBackStack(null)
      .commit()
  }

  override def onBackStackChanged(): Unit = {
    val enableBack = getFragmentManager().getBackStackEntryCount() != 0

    getActionBar().setHomeButtonEnabled(enableBack)
    getActionBar().setDisplayHomeAsUpEnabled(enableBack)
  }

}
