package com.ismartcoding.plain.ui.views.richtext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ismartcoding.plain.R

class CommandAdapter(context: Context) : SocialArrayAdapter<Suggestion>(context, R.layout.item_suggestion, R.id.text) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder
        when (view) {
            null -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_suggestion, parent, false)
                holder = ViewHolder(view!!)
                view.tag = holder
            }
            else -> holder = view.tag as ViewHolder
        }
        getItem(position)?.let { item -> holder.textView.text = item.value }
        return view
    }

    override fun convertToString(`object`: Suggestion): CharSequence {
        return `object`.value
    }

    private class ViewHolder(itemView: View) {
        val textView: TextView = itemView.findViewById(R.id.text)
    }
}
