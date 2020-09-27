package com.example.top10downloader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ViewHolder(v: View) {

    val tvTitle: TextView = v.findViewById(R.id.tvTitle)
    val tvDuration: TextView = v.findViewById(R.id.tvDuration)
    val tvSummary: TextView = v.findViewById(R.id.tvSummary)
    val tvPubDate: TextView = v.findViewById(R.id.tvPubDate)

}

class FeedAdapter(context: Context, private val resource: Int, private val applications: List<FeedEntry>) :
    ArrayAdapter<FeedEntry>(context, resource) {

    private val inflater = LayoutInflater.from(context)


    override fun getCount(): Int {
        return applications.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentApp = applications[position]

        viewHolder.tvTitle.text = currentApp.podcastTitle
        viewHolder.tvDuration.text = currentApp.podcastLength
        viewHolder.tvSummary.text = currentApp.summary
        viewHolder.tvPubDate.text = currentApp.pubDate

        return view

    }
}