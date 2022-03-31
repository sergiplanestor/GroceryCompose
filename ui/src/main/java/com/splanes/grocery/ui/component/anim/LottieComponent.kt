package com.splanes.grocery.ui.component.anim

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.splanes.grocery.utils.scope.applyIfNotNull

@Composable
fun LottieComponent(
    @RawRes animRawRes: Int,
    modifier: Modifier = Modifier,
    size: Dp? = null,
    autoplay: Boolean = true,
    loop: Boolean = true,
    clipSpec: LottieClipSpec? = null,
    speed: Float = 1f,
    iterations: Int = LottieConstants.IterateForever,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animRawRes))
    LottieAnimation(
        modifier = modifier.applyIfNotNull(size) { dp -> size(dp) },
        composition = composition,
        isPlaying = autoplay,
        restartOnPlay = loop,
        clipSpec = clipSpec,
        speed = speed,
        contentScale = contentScale,
        alignment = alignment,
        iterations = iterations,
    )
}