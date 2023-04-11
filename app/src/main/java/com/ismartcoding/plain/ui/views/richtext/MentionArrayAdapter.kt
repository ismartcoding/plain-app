package com.ismartcoding.plain.ui.views.richtext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ismartcoding.plain.R

class MentionArrayAdapter(context: Context) :
    SocialArrayAdapter<Mention>(context, R.layout.layout_socialview_mention, R.id.name) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cv = convertView
        val holder: ViewHolder
        if (cv == null) {
            cv = LayoutInflater.from(context).inflate(R.layout.layout_socialview_mention, parent, false)
            holder = ViewHolder(cv)
            cv.tag = holder
        } else {
            holder = cv.tag as ViewHolder
        }
        val item = getItem(position)
        if (item != null) {
            holder.usernameView.text = item.name
            Glide.with(holder.avatarView)
                .load(item.avatar)
                .placeholder(holder.avatarView.drawable)
                .into(holder.avatarView)
        }
        return cv!!
    }

    private class ViewHolder(itemView: View) {
        val avatarView: ImageView = itemView.findViewById(R.id.avatar)
        val usernameView: TextView = itemView.findViewById(R.id.name)
    }
}