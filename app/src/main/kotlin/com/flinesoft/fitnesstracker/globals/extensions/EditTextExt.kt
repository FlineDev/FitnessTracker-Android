package com.flinesoft.fitnesstracker.globals.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

// TODO: move all the cursor, skip logic out of this callback – use a library for input formatting
fun EditText.afterTextChanged(skipDeletion: Char? = null, afterTextChanged: (String, TextWatcher) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        private var cursorPos: Int = 0
        private var skipCallback: Boolean = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            cursorPos = start + after

            if (skipDeletion == null || s == null) return
            if (s.length > cursorPos && s[cursorPos] == skipDeletion) {
                skipCallback = true
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            if (!skipCallback) {
                afterTextChanged.invoke(editable.toString(), this)
            } else {
                setTextIgnoringTextWatcher(editable.toString().replaceRange(cursorPos, cursorPos, skipDeletion.toString()), this)
            }

            setSelection(cursorPos)
            skipCallback = false
        }
    })
}

fun EditText.setTextIgnoringTextWatcher(text: String, textWatcher: TextWatcher) {
    removeTextChangedListener(textWatcher)
    setText(text)
    addTextChangedListener(textWatcher)
}
