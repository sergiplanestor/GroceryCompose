package com.splanes.grocery.data.utils.firebase.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal fun eventListener(
    onFailure: (DatabaseError) -> Unit = { err -> throw err.toException() },
    onSuccess: (DataSnapshot) -> Unit = {},
) = object : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        onSuccess(snapshot)
    }

    override fun onCancelled(error: DatabaseError) {
        onFailure(error)
    }
}

suspend fun <T> DatabaseReference.fetch(
    onFailure: (DatabaseError) -> Throwable = { err -> throw err.toException() },
    onSuccess: (DataSnapshot) -> T
): T =
    suspendCoroutine { continuation ->
        addListenerForSingleValueEvent(
            eventListener(
                onSuccess = { continuation.resume(onSuccess(it)) },
                onFailure = { continuation.resumeWithException(onFailure(it)) },
            )
        )
    }
