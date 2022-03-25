package com.splanes.grocery.data.utils.firebase.database

import com.google.firebase.database.DataSnapshot
import com.splanes.grocery.data.utils.gson.fromJson

inline fun <reified T> DataSnapshot.fromJson(): T =
    value?.toString().orEmpty().fromJson()