package pt.pimentelfonseca.agilescalaandroid.app.ui;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pt.pimentelfonseca.agilescalaandroid.app.R;
import pt.pimentelfonseca.agilescalaandroid.app.models.Post;


public class PostListFragment extends ListFragment {

    public final static String BUNDLE_MODEL_JSON = "model_json";
    public final static int MENU_ITEM_EDIT = 1;
    public final static int MENU_ITEM_DELETE = 2;

    public final static int REQUEST_EDIT = 1;

    public List<Post> mItems;
    public PostListAdapter mListAdapter;

    public static PostListFragment newInstance(Post model) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_MODEL_JSON, new Gson().toJson(model));

        PostListFragment fragment = new PostListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PostListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setDividerHeight(0);

        // TODO: Load real objects from a database
        mItems = new ArrayList<Post>();
        for (int i = 0; i < 4 ; i++)
        {
            Post post = new Post();
            post.date = new Date();

            mItems.add(post);
        }

        mListAdapter = new PostListAdapter(getActivity(), mItems);
        setListAdapter(mListAdapter);
    }

    @Override
    public void onListItemClick(ListView listView, View viw, int position, long id) {

        Post model = mItems.get(position);
        FragmentTransaction transaction = super.getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in, R.animator.slide_out);

        PostFragment postFragment = PostFragment.newInstance(model, MainActivity.class);

        transaction.replace(R.id.main_container, postFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
