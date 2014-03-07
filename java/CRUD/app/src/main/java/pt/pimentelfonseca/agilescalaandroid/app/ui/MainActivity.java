package pt.pimentelfonseca.agilescalaandroid.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import pt.pimentelfonseca.agilescalaandroid.app.R;

public class MainActivity extends Activity implements PostFragment.PostDeleteHandler {

    PostListFragment mPostListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mPostListFragment = new PostListFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container, mPostListFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
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
}
