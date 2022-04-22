package com.splanes.grocery.ui.feature.auth.component.subcomponents

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.anim.AnimationSideEffect
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.anim.AnimDefaults
import com.splanes.grocery.ui.utils.resources.Drawables
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.dpValue
import com.splanes.grocery.ui.utils.resources.painter

@Composable
fun ColumnScope.AuthHeaderComponent(onAnimFinish: () -> Unit) {

    val initTopSpace = rememberInitialTopSpace()
    val alphaAnim = remember { Animatable(initialValue = .25f) }
    val topSpaceAnim = remember { Animatable(initialValue = initTopSpace) }

    Space { topSpaceAnim.value.dp }
    Space { large }
    Surface(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .alpha(alphaAnim.value),
        color = palette { onPrimary },
        shape = CircleShape
    ) {
        Box(modifier = Modifier.padding(all = dp { huge }), contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier.size(65.dp),
                painter = painter { Drawables.ic_grocery_bag },
                tint = palette { primary },
                contentDescription = null
            )
        }
    }
    Space { large }

    AnimationSideEffect(
        AnimDefaults.SideEffectUi(
            anim = alphaAnim,
            target = .9f,
            duration = 1000,
        ),
        AnimDefaults.SideEffectUi(
            anim = topSpaceAnim,
            target = .0f,
            duration = 1500,
            onFinish = onAnimFinish
        )
    )
}

@Composable
private fun rememberInitialTopSpace(): Float {
    val halfScreen = LocalConfiguration.current.screenHeightDp / 2
    val halfImage = ((dpValue<Float> { huge } * 2) + 80.dp.value) / 2
    val margin = dpValue<Float> { large }
    return remember { halfScreen - halfImage - margin }
}