package com.ismartcoding.plain.ui.helpers

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.ViewPageListBinding
import com.ismartcoding.plain.features.file.FileSortBy
import com.ismartcoding.plain.features.file.MediaType
import com.ismartcoding.plain.ui.extensions.highlightTitle
import com.ismartcoding.plain.ui.extensions.unhighlightTitle
import com.ismartcoding.plain.ui.models.FilteredItemsViewModel

object FileSortHelper {
    fun getSelectedSortItem(menu: Menu, sortBy: FileSortBy): MenuItem {
        return when (sortBy) {
            FileSortBy.DATE_ASC -> {
                menu.findItem(R.id.sort_oldest_first)
            }
            FileSortBy.DATE_DESC -> {
                menu.findItem(R.id.sort_newest_first)
            }
            FileSortBy.NAME_ASC -> {
                menu.findItem(R.id.sort_name_asc)
            }
            FileSortBy.NAME_DESC -> {
                menu.findItem(R.id.sort_name_desc)
            }
            FileSortBy.SIZE_ASC -> {
                menu.findItem(R.id.sort_smallest_first)
            }
            FileSortBy.SIZE_DESC -> {
                menu.findItem(R.id.sort_largest_first)
            }
        }
    }

    private fun getSortBy(mediaType: MediaType): FileSortBy {
        return when (mediaType) {
            MediaType.VIDEO -> {
                LocalStorage.videoSortBy
            }
            MediaType.IMAGE -> {
                LocalStorage.imageSortBy
            }
            MediaType.AUDIO -> {
                LocalStorage.audioSortBy
            }
            MediaType.FILE -> {
                LocalStorage.fileSortBy
            }
        }
    }

    private fun setSortBy(mediaType: MediaType, sortBy: FileSortBy) {
        when (mediaType) {
            MediaType.VIDEO -> {
                LocalStorage.videoSortBy = sortBy
            }
            MediaType.IMAGE -> {
                LocalStorage.imageSortBy = sortBy
            }
            MediaType.AUDIO -> {
                LocalStorage.audioSortBy = sortBy
            }
            MediaType.FILE -> {
                LocalStorage.fileSortBy = sortBy
            }
        }
    }

    fun sort(
        context: Context,
        menu: Menu,
        mediaType: MediaType,
        viewModel: FilteredItemsViewModel,
        list: ViewPageListBinding,
        sortBy: FileSortBy
    ) {
        getSelectedSortItem(menu, getSortBy(mediaType)).unhighlightTitle()
        setSortBy(mediaType, sortBy)
        getSelectedSortItem(menu, getSortBy(mediaType)).highlightTitle(context)
        viewModel.offset = 0
        list.page.refresh()
    }

    fun bindSortMenuItemClick(
        context: Context,
        menu: Menu,
        menuItem: MenuItem,
        mediaType: MediaType,
        viewModel: FilteredItemsViewModel,
        list: ViewPageListBinding,
    ) {
        when (menuItem.itemId) {
            R.id.sort_newest_first -> {
                sort(context, menu, mediaType, viewModel, list, FileSortBy.DATE_DESC)
            }
            R.id.sort_oldest_first -> {
                sort(context, menu, mediaType, viewModel, list, FileSortBy.DATE_ASC)
            }
            R.id.sort_largest_first -> {
                sort(context, menu, mediaType, viewModel, list, FileSortBy.SIZE_DESC)
            }
            R.id.sort_smallest_first -> {
                sort(context, menu, mediaType, viewModel, list, FileSortBy.SIZE_ASC)
            }
            R.id.sort_name_asc -> {
                sort(context, menu, mediaType, viewModel, list, FileSortBy.NAME_ASC)
            }
            R.id.sort_name_desc -> {
                sort(context, menu, mediaType, viewModel, list, FileSortBy.NAME_DESC)
            }
        }
    }
}