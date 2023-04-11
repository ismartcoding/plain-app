package com.ismartcoding.plain.ui.views.richtext

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import java.util.*

@Suppress("UNCHECKED_CAST")
abstract class SocialArrayAdapter<T>(context: Context, resource: Int, textViewResourceId: Int) : ArrayAdapter<T>(context, resource, textViewResourceId) {
    private var filter = SocialFilter()
    private val tempItems: MutableList<T> = ArrayList()
    override fun add(`object`: T?) {
        super.add(`object`)
        if (`object` != null) {
            tempItems.add(`object`)
        }
    }

    override fun addAll(collection: Collection<T>) {
        super.addAll(collection)
        tempItems.addAll(collection)
    }

    override fun remove(`object`: T?) {
        super.remove(`object`)
        tempItems.remove(`object`)
    }

    override fun clear() {
        super.clear()
        tempItems.clear()
    }

    open fun convertToString(`object`: T): CharSequence {
        return `object`.toString()
    }

    override fun getFilter(): Filter {
        return filter
    }

    private inner class SocialFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            if (constraint.isNullOrEmpty()) {
                return results
            }
            val filteredItems: MutableList<T> = ArrayList()
            for (item in tempItems) {
                if (convertToString(item).contains(constraint, ignoreCase = true)) {
                    filteredItems.add(item)
                }
            }
            results.values = filteredItems
            results.count = filteredItems.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val list = results?.values as? List<T>
            if (list != null && list.isNotEmpty()) {
                super@SocialArrayAdapter.clear()
                super@SocialArrayAdapter.addAll(list)
                notifyDataSetChanged()
            }
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            if (resultValue == null) {
                return ""
            }
            return convertToString(resultValue as T)
        }
    }
}