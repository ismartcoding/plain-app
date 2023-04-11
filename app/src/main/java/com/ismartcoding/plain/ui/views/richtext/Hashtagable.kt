package com.ismartcoding.plain.ui.views.richtext

/**
 * Abstract hashtag to be used with [com.ismartcoding.plain.views.socialview.HashtagArrayAdapter].
 */
interface Hashtagable {
    /**
     * Unique id of this hashtag.
     */
    val id: CharSequence

    /**
     * Optional count, located right to hashtag name.
     */
    val count: Int
}