package pt.pimentelfonseca.agilescalaandroid.app.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pt.pimentelfonseca.agilescalaandroid.app.R;
import pt.pimentelfonseca.agilescalaandroid.app.models.Author;
import pt.pimentelfonseca.agilescalaandroid.app.models.Post;

public class EditPostFragment extends Fragment {

    public static final String BUNDLE_MODEL_JSON = "model_json";
    public static final String BUNDLE_CREATE_NEW = "create_new";
    public Button mDateButton;
    public TextView mTitle;
    public TextView mNumberOfLikes;
    public Post mModel;

    public View mAuthorView;
    public View mCommentsView;


    private final View.OnClickListener mActionBarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.action_cancel:
                    getActivity().setResult(EditPostActivity.RESULT_NOTHING_CHANGED);
                    getActivity().finish();
                    break;
                case R.id.action_done:
                    mModel.Title = mTitle.getText().toString();
                    mModel.numberOfLikes = Integer.parseInt(mNumberOfLikes.getText().toString());

                    Intent data = new Intent();
                    data.putExtra(EditPostFragment.BUNDLE_MODEL_JSON, new Gson().toJson(mModel));

                    getActivity().setResult(EditPostActivity.RESULT_EDIT_OCCURRED, data);
                    getActivity().finish();
                    break;
            }
        }
    };


    public static EditPostFragment newInstance(Post model) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_MODEL_JSON, new Gson().toJson(model));

        EditPostFragment fragment = new EditPostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EditPostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getBoolean(BUNDLE_CREATE_NEW))
            {
                mModel = new Post();
            }
            else
            {
                String json = getArguments().getString(BUNDLE_MODEL_JSON);
                mModel = new Gson().fromJson(json, Post.class);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View actionBarButtons = inflater.inflate(R.layout.actionbar_edit_cancel_done, new LinearLayout(getActivity()), false);

        View cancelActionView = actionBarButtons.findViewById(R.id.action_cancel);
        cancelActionView.setOnClickListener(mActionBarListener);
        View doneActionView = actionBarButtons.findViewById(R.id.action_done);
        doneActionView.setOnClickListener(mActionBarListener);
        Log.e("", "");
        getActivity().getActionBar().setCustomView(actionBarButtons);
        getActivity().getActionBar().setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);


        View view = inflater.inflate(R.layout.fragment_post, container, false);

        View postView = inflater.inflate(R.layout.fragment_edit_post, container, false);

        FrameLayout postFrameLayout = (FrameLayout) view.findViewById(R.id.post_container);
        postFrameLayout.addView(postView);

        mTitle = (TextView) postView.findViewById(R.id.create_post_title);

        mNumberOfLikes = (TextView) postView.findViewById(R.id.create_post_number_of_likes);

        mDateButton = (Button) postView.findViewById(R.id.create_post_date);
        mDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mModel.date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        mModel.date = calendar.getTime();
                        mDateButton.setText(DateFormat.format("dd-MM-yyyy", mModel.date));
                    }
                }, year, month, day).show();
            }
        });

        mAuthorView = inflater.inflate(R.layout.fragment_view_author, container, false);

        FrameLayout postAuthorFrameLayout = (FrameLayout) view.findViewById(R.id.create_post_author_container);
        postAuthorFrameLayout.addView(mAuthorView);

        Button mSetAuthorButton = (Button) postView.findViewById(R.id.create_post_set_author);
        mSetAuthorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Choose the Author...");

                // TODO: Load real objects from a database
                final ArrayList<Post> mItems = new ArrayList<Post>();
                for (int i = 0; i < 4; i++) {
                    Post post = new Post();
                    post.date = new Date();

                    mItems.add(post);
                }

                alertDialogBuilder.setAdapter(new PostListAdapter(getActivity(), mItems), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mModel.author = mItems.get(which);
                        Author author = new Author();
                        author.Name = "Author " + which;
                        mModel.author = author;
                        displayAuthor();
                    }
                });
                alertDialogBuilder.setPositiveButton("New", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), EditPostActivity.class);
                        intent.putExtra(EditPostFragment.BUNDLE_CREATE_NEW, true);

                        startActivityForResult(intent, 1);
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });

        if (mModel != null)
        {
            mTitle.setText(mModel.Title);
            mNumberOfLikes.setText("" + mModel.numberOfLikes);
            mDateButton.setText(DateFormat.format("dd-MM-yyyy", mModel.date));
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        new Intent(Intent.ACTION_NEW_OUTGOING_CALL);

        getActivity().getActionBar().setCustomView(null);
    }

    public void displayAuthor()
    {
        TextView name = (TextView) mAuthorView.findViewById(R.id.author_name);
        name.setText(mModel.author.Name);
    }
}
