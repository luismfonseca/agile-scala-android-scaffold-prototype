package pt.pimentelfonseca.agilescalaandroid.app.ui;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import pt.pimentelfonseca.agilescalaandroid.app.R;

public class EditPostActivity extends Activity {

    public static final int RESULT_EDIT_OCCURRED = 1;
    public static final int RESULT_NOTHING_CHANGED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_post);
        if (savedInstanceState == null) {
            Fragment fragment = new EditPostFragment();
            fragment.setArguments(getIntent().getExtras());
            
            getFragmentManager().beginTransaction()
                    .add(R.id.edit_post_container,  fragment)
                    .commit();
        }
    }

}
