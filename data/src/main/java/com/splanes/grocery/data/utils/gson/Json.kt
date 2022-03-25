package com.splanes.grocery.data.utils.gson

import com.google.gson.Gson

fun <T> T.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, T::class.java)