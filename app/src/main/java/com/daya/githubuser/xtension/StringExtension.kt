package com.daya.githubuser.xtension

import android.util.Patterns
import android.webkit.URLUtil

fun String.trimLocationName(): String {
    return if (this.length > 20) {
        this.substring(0,20).plus("...")
    } else {
        this
    }
}

fun String.avatarForMainApp(): Int {
    val array = this.split("-")
    return array.first().toInt()
}

fun String.isValidUrl(): Boolean {
    return URLUtil.isValidUrl(this) && Patterns.WEB_URL.matcher(this).matches()
}

fun String.capitalized() : String {
    return this.replaceFirstChar(Char::uppercase)
}