package com.splanes.grocery.data.utils.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.splanes.grocery.data.utils.gson.fromJson
import com.splanes.grocery.data.utils.gson.toJson

fun <T> SharedPreferences.putJson(id: String, value: T) =
    edit { putString(id, value.toJson()) }

inline fun <reified T> SharedPreferences.getJson(id: String, default: String = ""): T =
    getString(id, default)
        .orEmpty()
        .fromJson()

inline fun <reified T> SharedPreferences.getJsonOrNull(id: String, default: String = ""): T? =
    runCatching<T> { getJson(id, default) }.getOrNull()