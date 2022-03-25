package com.splanes.grocery.utils.logger.tree

import timber.log.Timber

open class DebugTreeLog internal constructor(): Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        println("===== ${LoggerTree.titleByPrior(priority)} ===== >>>")
        super.log(priority, tag, message, t)
        println("<<< ===== ${LoggerTree.titleByPrior(priority)} =====")
    }
}