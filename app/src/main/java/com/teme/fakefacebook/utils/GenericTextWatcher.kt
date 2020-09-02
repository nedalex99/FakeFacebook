package com.teme.fakefacebook.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.teme.fakefacebook.R

class GenericTextWatcher(val view: View) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        when(view.id){
            R.id.first_name_et -> {

            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }
}