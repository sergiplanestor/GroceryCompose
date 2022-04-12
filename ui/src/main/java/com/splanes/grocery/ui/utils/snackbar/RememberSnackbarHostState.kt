package com.splanes.grocery.ui.utils.snackbar

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberSnackbarHostState(): SnackbarHostState = remember {
    SnackbarHostState()
}