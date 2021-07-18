package com.daya.githubuser.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableStringField

object NullAbleStringFieldAdapter {
    @ToJson
    fun toJson(@NullableStringField value: String?): String? {
        return value
    }

    @FromJson
    @NullableStringField
    fun fromJson(@javax.annotation.Nullable data: String?): String {
        return data ?: ""
    }
}