package pt.pimentelfonseca.agilescalaandroid.app.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import pt.pimentelfonseca.agilescalaandroid.app.R;
import pt.pimentelfonseca.luis.agilescalaandroid.ChangeToFragmentHandler;

public class PostMainActivity
    extends Activity
    implements PostFragment.PostDeleteHandler, ChangeToFragmentHandler, FragmentManager.OnBackStackChangedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container, new PostListFragment())
                    .commit();
        }
        getFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.main_container);

        if (currentFragment.getClass() == PostListFragment.class)
        {
            getMenuInflater().inflate(R.menu.main_post, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                break;

            case R.id.menu_main_new_post:
                Intent intent = new Intent(this, EditPostActivity.class);
                intent.putExtra(EditPostFragment.BUNDLE_CREATE_NEW, true);

                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPostDeleteHandler() {
        getFragmentManager().popBackStack();
    }

    public void onChangeToFragment(Fragment fragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in, R.animator.slide_out);
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackStackChanged() {
        boolean enableBack = getFragmentManager().getBackStackEntryCount() != 0;

        getActionBar().setHomeButtonEnabled(enableBack);
        getActionBar().setDisplayHomeAsUpEnabled(enableBack);
    }
}
