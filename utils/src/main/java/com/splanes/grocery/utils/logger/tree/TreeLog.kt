package com.splanes.grocery.utils.logger.tree

import android.os.Build
import android.util.Log
import timber.log.Timber

open class TreeLog internal constructor() : Timber.Tree() {

    private val fqcnIgnore = listOf(
        Timber::class.java.name,
        Timber.Forest::class.java.name,
        Timber.Tree::class.java.name,
        Timber.DebugTree::class.java.name,
        LoggerTree::class.java.name
    )

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val tagFormatted = tag ?: Throwable().stackTrace
            .first { it.className !in fqcnIgnore }
            .let(::createStackElementTag)
            .orEmpty()

        println("===== ${LoggerTree.titleByPrior(priority)} ===== >>>")
        print(priority, tagFormatted, message, t)
        println("<<< ===== ${LoggerTree.titleByPrior(priority)} =====")
    }

    private fun print(priority: Int, tag: String, message: String, t: Throwable?) {
        if (message.length < LoggerTree.MAX_LOG_LENGTH) {
            if (priority == Log.ASSERT) {
                Timber.tag(tag).wtf(message)
            } else {
                Log.println(priority, tag, message)
            }
            return
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        var i = 0
        val length = message.length
        while (i < length) {
            var newline = message.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = newline.coerceAtMost(i + LoggerTree.MAX_LOG_LENGTH)
                val part = message.substring(i, end)
                if (priority == Log.ASSERT) {
                    Timber.tag(tag).wtf(part)
                } else {
                    Log.println(priority, tag, part)
                }
                i = end
            } while (i < newline)
            i++
        }
    }

    private fun createStackElementTag(element: StackTraceElement): String? {
        var tag = element.className.substringAfterLast('.')
        val m = LoggerTree.ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        // Tag length limit was removed in API 26.
        return if (tag.length <= LoggerTree.MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= 26) {
            tag
        } else {
            tag.substring(0, LoggerTree.MAX_TAG_LENGTH)
        }
    }
}