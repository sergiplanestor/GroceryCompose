package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.splanes.grocery.ui.R
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.theme.UiTheme

@Composable
fun AuthLoaderComponent(
    message: String,
    background: Color = color { tertiaryContainer }
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_grocery_cart))
    Surface(
        shape = shape(size = 12),
        color = background.alpha(.35)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dp { mediumLarge }),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Space { medium }
            Text(
                text = message,
                style = bodyStyle { large }
            )
            Space { medium }
            LottieAnimation(
                modifier = Modifier.size(50.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
            Space { medium }
        }
    }
}



@Composable
@Preview(
    name = "AuthLoaderComponent",
    widthDp = 300,
    heightDp = 500,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewAuthLoaderComponent() {
    UiTheme.AppTheme {
        AuthLoaderComponent("Loading...")
    }
}