package com.splanes.grocery.ui.feature.markets.component.subcomponent.form.location

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.splanes.grocery.utils.logger.utils.throwMessageNonFatal

@SuppressLint("ComposableNaming")
interface PermissionRequestListener {

    @Composable
    fun granted()

    @Composable
    fun denied(permissions: List<LocationPermissionRequired>)
}

@SuppressLint("ComposableNaming")
fun PermissionRequestListener(
    denied: @Composable (permissions: List<LocationPermissionRequired>) -> Unit = {},
    granted: @Composable () -> Unit = {},
): PermissionRequestListener = object : PermissionRequestListener {

    @Composable
    override fun granted() {
        granted()
    }

    @Composable
    override fun denied(permissions: List<LocationPermissionRequired>) {
        throwMessageNonFatal {
            "Denied the following requested permissions: [" + buildString {
                permissions.forEachIndexed { index, permission ->
                    if (index != 0) append(", ")
                    append(permission.value)
                }
            } + "]."
        }
        denied(permissions)
    }
}

