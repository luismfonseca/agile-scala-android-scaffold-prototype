package pt.pimentelfonseca.agilescalaandroid.app.ui

import android.app.Activity
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import android.text.format.DateFormat
import java.util.Date


import pt.pimentelfonseca.agilescalaandroid.app.models.Simple
import pt.pimentelfonseca.agilescalaandroid.app.R

class SimpleListAdapter(val context: Activity, val items: Array[Simple]) extends ArrayAdapter[Simple](context, R.layout.item_simple, items)  {

  case class ViewHolder(dateAsFirst: TextView, stringAsSecond: TextView, placeholder: ImageView)

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {

    // Reuse views
    val rowView =
      if (convertView == null) {
        val layoutInflater = LayoutInflater.from(context)
        val newRowView = layoutInflater.inflate(R.layout.item_simple, null)

        // Configure view holder
        val viewHolder = new ViewHolder(
          newRowView.findViewById(R.id.item_simple_date_as_first).asInstanceOf[TextView],
          newRowView.findViewById(R.id.item_simple_string_as_second).asInstanceOf[TextView],

          newRowView.findViewById(R.id.item_simple_placeholder).asInstanceOf[ImageView]
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
      viewHolder.dateAsFirst.setText(DateFormat.format("dd-MM-yyyy", items(position).dateAsFirst))
      viewHolder.stringAsSecond.setText(items(position).stringAsSecond)

    }

    return rowView
  }

}
