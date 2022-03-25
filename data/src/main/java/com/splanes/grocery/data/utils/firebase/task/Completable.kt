package com.splanes.grocery.data.utils.firebase.task

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun <T> Task<T>.errorCause(): Throwable = exception ?: TaskUnknownError

fun <T> Task<T>.continueOnComplete(
    onFailure: (Throwable) -> Unit = {},
    onCancel: (TaskCanceledError) -> Unit = {},
    onSuccess: (T) -> Unit = {},
) = apply {
    addOnCompleteListener { task ->
        when {
            task.isCanceled -> onCancel(TaskCanceledError)
            task.isSuccessful -> onSuccess(task.result)
            else -> onFailure(task.errorCause())
        }
    }
}

suspend fun <T> completable(
    failure: (Throwable) -> Throwable = { it },
    block: () -> Task<T>,
): T =
    suspendCoroutine { continuation ->
        block().continueOnComplete(
            onSuccess = { continuation.resume(it) },
            onFailure = { continuation.resumeWithException(failure(it)) },
            onCancel = { continuation.resumeWithException(failure(TaskCanceledError)) }
        )
    }

suspend fun completeWithSuccess(
    throwOtherwise: Boolean = true,
    failure: (Throwable) -> Throwable = { it },
    block: () -> Task<*>
): Boolean =
    suspendCoroutine { continuation ->
        block().addOnCompleteListener { task ->
            when {
                task.isCanceled && throwOtherwise -> continuation.resumeWithException(
                    TaskCanceledError
                )
                task.isComplete || !throwOtherwise -> continuation.resume(task.isSuccessful)
                else -> {
                    if (task.exception != null) {
                        task.exception?.let(failure)?.run(continuation::resumeWithException)
                    } else {
                        continuation.resume(false)
                    }
                }
            }
        }
    }