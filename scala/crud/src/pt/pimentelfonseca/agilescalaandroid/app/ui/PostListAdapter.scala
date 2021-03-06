package pt.pimentelfonseca.agilescalaandroid.app.ui

import android.app.Activity
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView



import pt.pimentelfonseca.agilescalaandroid.app.models.Post
import pt.pimentelfonseca.agilescalaandroid.app.R

class PostListAdapter(val context: Activity, val items: Array[Post]) extends ArrayAdapter[Post](context, R.layout.item_post, items)  {

  case class ViewHolder(title: TextView, numberOfLikes: TextView, description: TextView, placeholder: ImageView)

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {

    // Reuse views
    val rowView =
      if (convertView == null) {
        val layoutInflater = LayoutInflater.from(context)
        val newRowView = layoutInflater.inflate(R.layout.item_post, null)

        // Configure view holder
        val viewHolder = new ViewHolder(
          newRowView.findViewById(R.id.item_post_title).asInstanceOf[TextView],
          newRowView.findViewById(R.id.item_post_number_of_likes).asInstanceOf[TextView],
          newRowView.findViewById(R.id.item_post_description).asInstanceOf[TextView],

          newRowView.findViewById(R.id.item_post_placeholder).asInstanceOf[ImageView]
        )
        newRowView.setTag(viewHolder)
        newRowView
      }
      else {
        convertView
      }

    // Fill data
    val viewHolder = rowView.getTag().asInstanceOf[ViewHolder]
    viewHolder.placeholder.setImageResource(R.drawable.ic_placeholder)
    if (items(position) != null) {
      viewHolder.title.setText(items(position).title)
      viewHolder.numberOfLikes.setText("" + items(position).numberOfLikes)
      viewHolder.description.setText(items(position).description)

    }

    return rowView
  }

}
