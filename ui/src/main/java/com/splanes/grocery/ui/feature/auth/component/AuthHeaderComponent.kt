package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.AnimationResult
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.resources.Drawables
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.dpValue
import com.splanes.grocery.ui.utils.resources.painter
import kotlinx.coroutines.launch

@Composable
fun ColumnScope.AuthHeaderComponent(onAnimFinish: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val initTopSpace = rememberInitialTopSpace()
    val alphaAnim = remember { Animatable(initialValue = .25f) }
    val topSpaceAnim = remember { Animatable(initialValue = initTopSpace) }

    Space { topSpaceAnim.value.dp }
    Space { large }
    Surface(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .alpha(alphaAnim.value),
        color = color { onPrimary },
        shape = CircleShape
    ) {
        Box(modifier = Modifier.padding(all = dp { huge }), contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier.size(65.dp),
                painter = painter { Drawables.ic_app_logo },
                tint = color { primary },
                contentDescription = null
            )
        }
    }
    Space { large }

    LaunchedEffect("AnimationSideEffect") {
        coroutineScope.launch {
            alphaAnim.launchAnim(target = .9, duration = 1000) {
                coroutineScope.launch {
                    topSpaceAnim.launchAnim(target = .0, duration = 1500, onFinish = onAnimFinish)
                }
            }
        }
    }
}

private suspend fun Animatable<Float, *>.launchAnim(target: Double, duration: Int, onFinish: () -> Unit) {
    animateTo(
        targetValue = target.toFloat(),
        animationSpec = tween(durationMillis = duration)
    ).takeIf {
        it.finished
    }?.run {
        onFinish()
    }
}

private val AnimationResult<*, *>.finished: Boolean
    get() = endReason == AnimationEndReason.Finished

@Composable
private fun rememberInitialTopSpace(): Float {
    val halfScreen = LocalConfiguration.current.screenHeightDp / 2
    val halfImage = ((dpValue<Float> { huge } * 2) + 80.dp.value) / 2
    val margin = dpValue<Float> { large }
    return remember { halfScreen - halfImage - margin }
}