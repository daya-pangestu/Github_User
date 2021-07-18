package com.daya.consumerapp.xtension

import android.util.Patterns
import android.webkit.URLUtil


fun String.trimLocationName(): String {
    return if (this.length > 20) {
        this.substring(0,20).plus("...")
    } else {
        this
    }
}


fun String.isValidUrl(): Boolean {
    return URLUtil.isValidUrl(this) && Patterns.WEB_URL.matcher(this).matches()
}