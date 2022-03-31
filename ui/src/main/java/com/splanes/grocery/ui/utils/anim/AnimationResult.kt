package com.splanes.grocery.ui.utils.anim

import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.AnimationResult

val AnimationResult<*, *>.finished: Boolean
    get() = endReason == AnimationEndReason.Finished