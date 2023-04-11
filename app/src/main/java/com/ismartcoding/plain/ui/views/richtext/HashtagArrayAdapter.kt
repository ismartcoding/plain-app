package com.ismartcoding.plain.ui.views.richtext

import android.content.Context
import com.ismartcoding.plain.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.ismartcoding.plain.features.locale.LocaleHelper

class HashtagArrayAdapter<T : Hashtagable>(context: Context) :
    SocialArrayAdapter<T>(context, R.layout.layout_socialview_hashtag, R.id.socialview_hashtag) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder
        var cv = convertView
        if (cv == null) {
            cv = LayoutInflater.from(context).inflate(R.layout.layout_socialview_hashtag, parent, false)
            holder = ViewHolder(cv)
            cv.tag = holder
        } else {
            holder = cv.tag as ViewHolder
        }
        val item = getItem(position)
        if (item != null) {
            holder.hashtagView.text = item.id
            if (item.count > 0) {
                holder.countView.visibility = View.VISIBLE
                val count = item.count
                holder.countView.text = LocaleHelper.getQuantityString(R.plurals.posts, count)
            } else {
                holder.countView.visibility = View.GONE
            }
        }
        return convertView!!
    }

    private class ViewHolder(itemView: View) {
        val hashtagView: TextView = itemView.findViewById(R.id.socialview_hashtag)
        val countView: TextView = itemView.findViewById(R.id.socialview_hashtag_count)

    }
}