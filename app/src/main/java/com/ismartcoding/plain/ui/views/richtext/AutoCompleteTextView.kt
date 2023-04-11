package com.ismartcoding.plain.ui.views.richtext

import android.content.Context
import android.text.*
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView

class AutoCompleteTextView(
    context: Context, attrs: AttributeSet? = null
) : AppCompatMultiAutoCompleteTextView(context, attrs) {
    val helper = SocialViewHelper(this)

    var hashtagAdapter: ArrayAdapter<Hashtagable>? = null
    var mentionAdapter: ArrayAdapter<Mention>? = null
    var commandAdapter: ArrayAdapter<Suggestion>? = null

    init {
        threshold = 1
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(s) && start < s.length) {
                    when (s[start]) {
                        ':' -> if (adapter != commandAdapter) {
                            setAdapter(commandAdapter)
                        }
                        '#' -> if (adapter != hashtagAdapter) {
                            setAdapter(hashtagAdapter)
                        }
                        '@' -> if (adapter != mentionAdapter) {
                            setAdapter(mentionAdapter)
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        setTokenizer(object : Tokenizer {
            private val chars = mutableListOf(':', '@', '#')
            override fun findTokenStart(text: CharSequence, cursor: Int): Int {
                var i = cursor
                while (i > 0 && !chars.contains(text[i - 1])) {
                    i--
                }
                while (i < cursor && text[i] == ' ') {
                    i++
                }

                // imperfect fix for dropdown still showing without symbol found
                if (i == 0 && isPopupShowing) {
                    dismissDropDown()
                }
                return i
            }

            override fun findTokenEnd(text: CharSequence, cursor: Int): Int {
                var i = cursor
                val len = text.length
                while (i < len) {
                    if (chars.contains(text[i])) {
                        return i
                    } else {
                        i++
                    }
                }
                return len
            }

            override fun terminateToken(text: CharSequence): CharSequence {
                var i = text.length
                while (i > 0 && text[i - 1] == ' ') {
                    i--
                }
                return if (i > 0 && chars.contains(text[i - 1])) {
                    text
                } else {
                    if (text is Spanned) {
                        val sp = SpannableString("$text")
                        TextUtils.copySpansFrom(text, 0, text.length, Any::class.java, sp, 0)
                        sp
                    } else {
                        "$text"
                    }
                }
            }
        })
    }
}