package com.splanes.grocery.ui.base

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.State as ComposeState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <E, M> UiStateComponent(
    uiState: ComposeState<Ui.State<E, M>>,
    modifier: Modifier = Modifier,
    animate: Boolean = true,
    transitionSpec: AnimatedContentScope<Ui.State<E, M>>.() -> ContentTransform = {
        uiStateComponentEnterTransition with uiStateComponentExitTransition
    },
    errorContent: @Composable E?.() -> Unit = { },
    stableContent: @Composable M.() -> Unit
) {
    val content: @Composable (Ui.State<E, M>) -> Unit = { state ->
        when (state) {
            is Ui.Stable -> stableContent(state.model)
            is Ui.Error -> errorContent(state.error)
        }
    }

    if (animate) {
        AnimatedContent(
            modifier = modifier,
            targetState = uiState.value,
            transitionSpec = transitionSpec,
            content = { state -> content(state) }
        )
    } else {
        content(uiState.value)
    }
}

@OptIn(ExperimentalAnimationApi::class)
private val uiStateComponentEnterTransition: EnterTransition
    get() = fadeIn(animationSpec = tween(durationMillis = 220, delayMillis = 90)) +
            scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90))

private val uiStateComponentExitTransition: ExitTransition
    get() = fadeOut(animationSpec = tween(90))