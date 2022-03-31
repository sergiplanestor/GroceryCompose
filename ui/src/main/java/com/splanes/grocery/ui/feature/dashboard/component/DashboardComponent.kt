package com.splanes.grocery.ui.feature.dashboard.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.splanes.grocery.ui.utils.resources.display

@Composable
fun DashboardComponent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Dashboard", style = display { medium })
    }
}