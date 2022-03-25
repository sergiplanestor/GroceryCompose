package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.viewport

@Composable
fun AuthContainerComponent(
    header: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color { primaryContainer })
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = viewport { mediumSmall },
                vertical = viewport { medium }),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header()
            Space { medium }
            content()
        }
    }
}