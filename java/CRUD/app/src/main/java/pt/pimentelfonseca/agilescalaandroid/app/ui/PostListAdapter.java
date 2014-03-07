package pt.pimentelfonseca.agilescalaandroid.app.ui;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pt.pimentelfonseca.agilescalaandroid.app.R;
import pt.pimentelfonseca.agilescalaandroid.app.models.Post;

public class PostListAdapter extends ArrayAdapter<Post> {

    private final Activity context;
    private final List<Post> items;

    static class ViewHolder {
        public TextView title;
        public TextView numberOfLikes;
        public TextView date;
        public ImageView placeholder;
    }

    public PostListAdapter(Activity context, List<Post> items) {
        super(context, R.layout.item_post, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // Reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_post, null);

            // Configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.placeholder = (ImageView) rowView.findViewById(R.id.item_post_placeholder);
            viewHolder.title = (TextView) rowView.findViewById(R.id.item_post_title);
            viewHolder.numberOfLikes = (TextView) rowView.findViewById(R.id.item_post_number_of_likes);
            viewHolder.date = (TextView) rowView.findViewById(R.id.item_post_date);
            rowView.setTag(viewHolder);
        }

        // Fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.placeholder.setImageResource(R.drawable.ic_placeholder);
        holder.title.setText(items.get(position).Title);
        holder.numberOfLikes.setText("" + items.get(position).numberOfLikes);
        holder.date.setText("" + DateFormat.format("dd-MM-yyyy", items.get(position).date));

        return rowView;
    }

}
