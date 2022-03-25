package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.splanes.grocery.ui.R
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.resources.body
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.toolkit.compose.ui.theme.UiTheme

@Composable
fun AuthLoaderComponent(message: String) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_grocery_cart))
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(all = dp { mediumLarge })
            .background(
                color = color { tertiaryContainer },
                shape = shape(size = 12)
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Space { medium }
            Text(
                text = message,
                style = body { large }
            )
            Space { medium }
            LottieAnimation(
                composition = composition,
                iterations = 1
            )
            Space { medium }
        }
    }
}

@Composable
@Preview(name = "p1", widthDp = 300, heightDp = 500)
fun PreviewAuthLoaderComponent() {
    UiTheme.AppTheme {
        AuthLoaderComponent("Loading...")
    }
}