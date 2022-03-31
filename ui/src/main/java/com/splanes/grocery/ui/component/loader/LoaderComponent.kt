package com.splanes.grocery.ui.component.loader

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.splanes.grocery.ui.component.anim.LottieComponent
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.title
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

@Composable
fun LoaderComponent(
    modifier: Modifier = Modifier,
    order: LoaderDefaults.Order = LoaderDefaults.AnimBeforeText,
    message: String? = null,
    messageStyle: TextStyle = title { large },
    messageAlign: TextAlign = TextAlign.Center,
    @RawRes animRawRes: Int? = null,
    animSize: Dp = LoaderDefaults.animSize { medium },
    containerShape: Shape = RectangleShape,
    contentColor: Color = color { onTertiaryContainer },
    containerColor: Color = color { tertiaryContainer },
    messageContent: (@Composable ColumnScope.() -> Unit)? = message?.let { text ->
        {
            LoaderMessageComponent(
                text = text,
                textColor = contentColor,
                textStyle = messageStyle,
                textAlign = messageAlign
            )
        }
    },
    animContent: (@Composable ColumnScope.() -> Unit)? = animRawRes?.let { anim ->
        { LottieComponent(animRawRes = anim, size = animSize) }
    },
    content: @Composable ColumnScope.() -> Unit = {
        when (order) {
            LoaderDefaults.AnimBeforeText -> animContent to messageContent
            LoaderDefaults.TextBeforeAnim -> messageContent to animContent
        }.let { (first, second) ->
            val hasFirst = first != null
            val hasSecond = second != null
            Space { mediumSmall }
            if (hasFirst) {
                first?.invoke(this)
            }
            if (hasSecond) {
                if (hasFirst) Space { mediumSmall }
                second?.invoke(this)
            }
            Space { mediumSmall }
        }
    }
) {
    Surface(
        modifier = modifier,
        shape = containerShape,
        color = containerColor.alpha(.35)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = dp { mediumLarge }),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
fun LoaderMessageComponent(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = title { large },
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        style = textStyle,
        textAlign = textAlign
    )
}