package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.spacer.VerticalSpace
import com.splanes.grocery.ui.utils.resources.Drawables
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.painter
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

@Composable
fun AuthHeaderComponent() {
    VerticalSpace { large }
    Surface(
        color = color { onPrimary }.alpha(.9),
        shape = CircleShape
    ) {
        Box(modifier = Modifier.padding(all = dp { huge }), contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier.size(80.dp),
                painter = painter { Drawables.ic_app_logo },
                tint = color { primary },
                contentDescription = null
            )
        }
    }
    VerticalSpace { large }
}