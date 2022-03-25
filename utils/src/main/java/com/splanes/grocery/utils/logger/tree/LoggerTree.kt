package com.splanes.grocery.utils.logger.tree

import android.util.Log
import java.util.regex.Pattern
import timber.log.Timber

object LoggerTree {

    private var tree: Timber.Tree? = null
    internal const val MAX_LOG_LENGTH = 4000
    internal const val MAX_TAG_LENGTH = 23
    internal val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")

    fun init(isDebug: Boolean): Timber.Tree = tree ?: run {
        if (isDebug) buildDebugTreeLog() else buildTreeLog()
    }.also { t -> tree = t }

    private fun buildDebugTreeLog(): Timber.Tree = DebugTreeLog()

    private fun buildTreeLog(): Timber.Tree = TreeLog()

    internal fun titleByPrior(priority: Int): String =
        when (priority) {
            Log.VERBOSE -> "VERBOSE"
            Log.ERROR -> "ERROR"
            Log.ASSERT -> "ASSERT"
            Log.WARN -> "WARNING"
            Log.DEBUG -> "DEBUG"
            Log.INFO -> "INFO"
            else -> ""
        }
}