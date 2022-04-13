package com.splanes.grocery.ui.utils.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.collectAsNullableState(initial: T? = null): State<T?> = collectAsState(initial = initial)