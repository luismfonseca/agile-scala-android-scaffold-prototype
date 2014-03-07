package pt.pimentelfonseca.agilescalaandroid.app.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import pt.pimentelfonseca.agilescalaandroid.app.R;
import pt.pimentelfonseca.agilescalaandroid.app.models.Post;

public class PostFragment extends Fragment {

    public final static String BUNDLE_MODEL_JSON = "model_json";
    public final static int MENU_ITEM_EDIT = 1;
    public final static int MENU_ITEM_DELETE = 2;

    public final static int REQUEST_EDIT = 1;

    public Post mModel;
    public TextView mModelTitle;
    public TextView mModelNumberOfLikes;
    public TextView mModelDate;

    public static <T extends Activity & PostDeleteHandler> PostFragment newInstance(Post model, Class<T> activityHandler) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_MODEL_JSON, new Gson().toJson(model));

        PostFragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (getArguments() != null) {
                String json = getArguments().getString(BUNDLE_MODEL_JSON);

                mModel = new Gson().fromJson(json, Post.class);
            }
        }
        else
        {
            String json = savedInstanceState.getString(BUNDLE_MODEL_JSON);

            mModel = new Gson().fromJson(json, Post.class);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_MODEL_JSON, new Gson().toJson(mModel));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mModelTitle = (TextView) view.findViewById(R.id.post_title);
        mModelNumberOfLikes = (TextView) view.findViewById(R.id.post_number_of_likes);
        mModelDate = (TextView) view.findViewById(R.id.post_date);

        displayPost();
        return view;
    }

    private void displayPost() {
        mModelTitle.setText(mModel.Title);
        mModelNumberOfLikes.setText("" + mModel.numberOfLikes);
        mModelDate.setText(DateFormat.format("dd-MM-yyyy", mModel.date));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem editMenu = menu.add(Menu.NONE, MENU_ITEM_EDIT, Menu.NONE, "Edit");
        editMenu.setIcon(android.R.drawable.ic_menu_edit);
        editMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        MenuItem deleteMenu = menu.add(Menu.NONE, MENU_ITEM_DELETE, Menu.NONE, "Delete");
        deleteMenu.setIcon(android.R.drawable.ic_menu_delete);
        deleteMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case MENU_ITEM_EDIT:

                Intent intent = new Intent(this.getActivity(), EditPostActivity.class);

                String PostInJson = new Gson().toJson(mModel);
                intent.putExtra(EditPostFragment.BUNDLE_MODEL_JSON, PostInJson);
                startActivityForResult(intent, REQUEST_EDIT);

                return true;
            case MENU_ITEM_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to delete this Post?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(PostFragment.this.getActivity(), "ok", Toast.LENGTH_LONG).show();

                                // TODO: Actually remove the object from database

                                ((PostDeleteHandler) getActivity()).onPostDeleteHandler();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case REQUEST_EDIT:
                if (resultCode == EditPostActivity.RESULT_EDIT_OCCURRED)
                {
                    String json = data.getExtras().getString(EditPostFragment.BUNDLE_MODEL_JSON);
                    mModel = new Gson().fromJson(json, Post.class);

                    // TODO: Save the edited object to the database
                    displayPost();
                }
                else// if (resultCode == EditPostActivity.RESULT_NOTHING_CHANGED)
                {
                    Toast.makeText(getActivity(), "Post edit was canceled.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public static interface PostDeleteHandler {

        public void onPostDeleteHandler();
    }
}
