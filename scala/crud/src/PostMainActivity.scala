package pt.pimentelfonseca.agilescalaandroid.app.ui

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import org.scaloid.common._
import android.graphics.Color
import android.view.MenuInflater

import pt.pimentelfonseca.agilescalaandroid.app.R

class PostMainActivity
  extends SActivity
  with PostFragment.PostDeleteHandler
  with ChangeToFragmentHandler
  with FragmentManager.OnBackStackChangedListener {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_post)

    if (savedInstanceState == null) {
        getFragmentManager()
          .beginTransaction()
          .add(R.id.post_main_container, new PostListFragment())
          .commit()
    }
    getFragmentManager().addOnBackStackChangedListener(this)
  }
  
  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    val currentFragment = getFragmentManager().findFragmentById(R.id.post_main_container);

    if (currentFragment.getClass() == classOf[PostListFragment])
    {
        getMenuInflater().inflate(R.menu.main_post, menu)
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

      case R.id.menu_main_new_post => {

        val intent = new Intent(this, classOf[EditPostActivity])
        intent.putExtra(EditPostFragment.BUNDLE_CREATE_NEW, true)

        startActivity(intent)
        true
      }
      case _ => super.onOptionsItemSelected(menuItem)
    }
  }


  override def onPostDeleteHandler(): Unit = {
    getFragmentManager().popBackStack()
  }

  override def onChangeToFragment(fragment: Fragment): Unit = {
    val fragmentTransaction = getFragmentManager().beginTransaction()
    fragmentTransaction.setCustomAnimations(R.animator.slide_in, R.animator.slide_out)
    fragmentTransaction.replace(R.id.post_main_container, fragment)
    fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
  }

  override def onBackStackChanged(): Unit = {
    val enableBack = getFragmentManager().getBackStackEntryCount() != 0

    getActionBar().setHomeButtonEnabled(enableBack)
    getActionBar().setDisplayHomeAsUpEnabled(enableBack)
  }

}