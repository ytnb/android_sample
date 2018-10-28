package com.example.ytnb.databinding2

import android.databinding.BindingAdapter
import android.widget.EditText

@BindingAdapter("showError","errorText")
fun EditText.setErrorText(showError: Boolean, errorText: String?) {
    error = if (showError) errorText else null
}

