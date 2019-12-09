package com.flinesoft.fitnesstracker.globals.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.afterTextChanged(afterTextChanged: (String, TextWatcher) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString(), this)
        }
    })
}

fun EditText.setTextIgnoringTextWatcher(text: String, textWatcher: TextWatcher) {
    removeTextChangedListener(textWatcher)
    setText(text)
    addTextChangedListener(textWatcher)
}
