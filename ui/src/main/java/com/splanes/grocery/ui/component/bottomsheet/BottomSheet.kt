package com.splanes.grocery.ui.component.bottomsheet

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.bottomsheet.BottomSheets.UiModel
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.icons.rounded
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.grocery.ui.utils.ripple.RippleStyle
import com.splanes.grocery.utils.scope.orNull
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

@Composable
fun BottomSheet(
    uiModel: UiModel,
    modifier: Modifier = Modifier
) {
    with(uiModel) {
        BottomSheetContainer(
            modifier = modifier,
            scrollable = scrollable,
            alignment = alignment
        ) { scrollState ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .heightIn(max = 56.dp)
                    .padding(horizontal = dp { mediumSmall }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                uiContent.title(this)
                uiContent.close(this)
            }
            uiContent.content(this, scrollState)
        }
    }
}

@Composable
fun BottomSheetContainer(
    scrollable: Boolean,
    alignment: Alignment.Horizontal,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.(ScrollState?) -> Unit
) {
    val scrollState = orNull(condition = scrollable) { rememberScrollState() }
    Column(
        modifier = scrollState?.let { modifier.verticalScroll(it) } ?: modifier,
        horizontalAlignment = alignment,
        content = { content(scrollState) }
    )
}

@Composable
fun BottomSheetCloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Icons.Source = Icons.rounded { Close },
    tint: Color = color { onSurface.alpha(.8) },
    size: Dp = Icons.SizeSmall.dp
) {
    RippleStyle(color = color { primary }) {
        Icons.Icon(
            modifier = modifier,
            source = icon,
            size = size,
            color = tint,
            onClick = onClick
        )
    }
}

@Composable
fun BottomSheetTitle(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = titleStyle { large },
    color: Color = color { primary },
    align: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier.padding(all = dp { mediumSmall }),
        text = text,
        style = style,
        color = color,
        textAlign = align
    )
}