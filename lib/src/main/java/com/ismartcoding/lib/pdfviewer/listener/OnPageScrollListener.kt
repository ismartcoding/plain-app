package com.ismartcoding.lib.pdfviewer.listener

import com.ismartcoding.lib.pdfviewer.PDFView

/**
 * Implements this interface to receive events from PDFView
 * when a page has been scrolled
 */
interface OnPageScrollListener {
    /**
     * Called on every move while scrolling
     *
     * @param page current page index
     * @param positionOffset see [com.github.barteksc.pdfviewer.PDFView.getPositionOffset]
     */
    fun onPageScrolled(page: Int, positionOffset: Float)
}