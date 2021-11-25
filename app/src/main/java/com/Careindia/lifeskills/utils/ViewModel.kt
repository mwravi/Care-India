package com.careindia.lifeskills.utils

public class ViewModel {
    private var text: String? = null
    fun getText(): String? {
        return text
    }

    fun setText(text: String?) {
        this.text = text
    }
}