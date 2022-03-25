package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.spacer.VerticalSpace
import com.splanes.grocery.ui.utils.anim.AnimDefaults
import com.splanes.grocery.ui.utils.anim.tween
import com.splanes.grocery.ui.utils.resources.Drawables
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.painter
import com.splanes.grocery.utils.post.post
import com.splanes.grocery.utils.time.Seconds
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

@Composable
fun AuthHeaderComponent(onAnimEnd: () -> Unit) {
    var isStarted by remember { mutableStateOf(false) }
    var isAnimEnabled by remember { mutableStateOf(true) }

    post(delay = AnimDefaults.DelayShort) { isStarted = true }
    post(delay = AnimDefaults.DelayShort + Seconds.millis(1)) {
        isAnimEnabled = false
        onAnimEnd()
    }

    val alpha by animateFloatAsState(
        targetValue = if (isStarted) 1f else 0f,
        animationSpec = tween(
            duration = Seconds.millis(1),
            delay = AnimDefaults.DelayShort
        )
    )
    VerticalSpace { large }
    AnimatedVisibility(
        visible = isAnimEnabled,
        enter = EnterTransition.None,
        exit = if (isAnimEnabled) {
            fadeOut(
                animationSpec = tween(
                    duration = AnimDefaults.DurationMedium,
                    delay = AnimDefaults.DelayShort
                )
            ) + shrinkVertically(
                shrinkTowards = Alignment.Top,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
            )
        } else {
            ExitTransition.None
        },
        content = { VerticalSpace(300.dp) }
    )

    AuthHeaderImage(alpha = alpha)
}

@Composable
private fun AuthHeaderImage(alpha: Float) {
    Surface(
        modifier = Modifier
            .background(
                color = color { onPrimaryContainer }.alpha(.25),
                shape = CircleShape
            )
            .alpha(alpha)
    ) {
        Image(
            modifier = Modifier.size(40.dp),
            painter = painter { Drawables.ic_app_logo },
            alpha = .75f,
            colorFilter = ColorFilter.tint(color = color { primary }),
            contentDescription = null
        )
    }
}