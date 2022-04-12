package com.splanes.grocery.ui.feature.auth.component.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.splanes.grocery.ui.R
import com.splanes.grocery.ui.component.anim.LottieComponent
import com.splanes.grocery.ui.component.loader.LoaderComponent
import com.splanes.grocery.ui.component.loader.Loaders
import com.splanes.grocery.ui.component.loader.LoaderMessageComponent
import com.splanes.grocery.ui.component.spacer.VerticalSpace
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.headlineStyle
import com.splanes.grocery.ui.utils.resources.plus
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.theme.UiTheme
import kotlinx.coroutines.delay

@Composable
fun AuthAutoSignInComponent(onSignIn: () -> Unit) {

    var doSignIn by remember { mutableStateOf(false) }
    LaunchedEffect(doSignIn) {
        delay(1500)
        doSignIn = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = string { Strings.user_sign_in },
            color = color { onPrimary },
            style = headlineStyle { medium },
            textAlign = TextAlign.Center,
            fontSize = headlineStyle { medium }.fontSize + 2
        )
        Space { large }
        LoaderComponent(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            animRawRes = R.raw.anim_grocery_cart,
            animSize = Loaders.animSize { large },
            containerColor = color { inverseSurface }.alpha(.8),
            contentColor = color { inverseOnSurface },
            containerShape = shape { large },
            animContent = { AuthAutoSignInLoadingAnim() },
            messageContent = { AuthAutoSignInLoadingMessage() }
        )
        Space { medium }
    }

    if (doSignIn) onSignIn()
}

@Composable
fun AuthAutoSignInLoadingAnim() {
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color { inverseOnSurface },
                        color { inverseOnSurface }.alpha(.6),
                        Color.Transparent
                    ),
                    tileMode = TileMode.Decal,
                ),
            )
            .padding(all = dp { large }),
        color = Color.Transparent
    ) {
        LottieComponent(
            modifier = Modifier.padding(bottom = dp { large }),
            animRawRes = R.raw.anim_grocery_cart,
            size = Loaders.animSize { large },
        )
    }
}

@Composable
fun AuthAutoSignInLoadingMessage() {
    VerticalSpace { small }
    LoaderMessageComponent(
        text = string { Strings.signing_in_user_loading_title },
        textColor = color { inverseOnSurface },
        textStyle = titleStyle { large }
    )
    VerticalSpace { mediumLarge }
    LoaderMessageComponent(
        text = string { Strings.signing_in_user_loading_msg },
        textColor = color { inverseOnSurface },
        textStyle = bodyStyle { large }
    )
    VerticalSpace { mediumSmall }
}


@Composable
@Preview(
    name = "AuthAutoSignInComponent",
    widthDp = 300,
    heightDp = 500,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewAuthAutoSignInComponent() {
    UiTheme.AppTheme {
        AuthAutoSignInComponent {}
    }
}