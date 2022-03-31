package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.viewport

@Composable
fun AuthContainerComponent(
    header: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.(ScrollState) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color { primary })
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = viewport { mediumSmall },
                    end = viewport { mediumSmall },
                    top = viewport { medium }
                )
                .background(color { primary })
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header()
            Space { medium }
            content(scrollState)
        }
    }
}