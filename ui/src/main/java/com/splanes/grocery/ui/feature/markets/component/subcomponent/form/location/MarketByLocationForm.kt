package com.splanes.grocery.ui.feature.markets.component.subcomponent.form.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun LocationPermissionsRequest(
    onGranted: @Composable () -> Unit,
    onDenied: @Composable (List<LocationPermissionRequired>) -> Unit
) {
    var isGrantedSideEffectLaunched by remember { mutableStateOf(false) }
    var deniedPermissionsListSideEffect: List<LocationPermissionRequired> by remember { mutableStateOf(emptyList()) }
    val permissions = locationPermissionsState(LocalContext.current)

    when {
        isGrantedSideEffectLaunched -> {
            isGrantedSideEffectLaunched = false
            onGranted()
            return
        }
        deniedPermissionsListSideEffect.isNotEmpty() -> {
            deniedPermissionsListSideEffect = emptyList()
            onDenied(deniedPermissionsListSideEffect)
            return
        }
    }

    when {
        permissions.values.all { granted -> granted } -> {
            onGranted()
        }
        else -> {
            val request = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
            ) { resultMap ->
                when {
                    resultMap.values.all { granted -> granted } -> {
                        isGrantedSideEffectLaunched = true
                    }
                    else -> {
                        deniedPermissionsListSideEffect = resultMap.keys
                            .filterNot { permission -> resultMap[permission] ?: false }
                            .map { permission -> LocationPermissionRequired.map(permission) }
                    }
                }
            }

            permissions.keys
                .filterNot { permissionRequired -> permissions[permissionRequired] ?: false }
                .map { permissionRequired ->  permissionRequired.value }
                .run { SideEffect { request.launch(toTypedArray()) } }
        }
    }
}

enum class LocationPermissionRequired(val value: String) {
    WifiState(value = Manifest.permission.ACCESS_WIFI_STATE),
    AccessFine(value = Manifest.permission.ACCESS_FINE_LOCATION),
    Coarse(value = Manifest.permission.ACCESS_COARSE_LOCATION);

    companion object {
        fun map(value: String): LocationPermissionRequired = values().first { it.value == value }
    }
}

private fun locationPermissionsState(context: Context): Map<LocationPermissionRequired, Boolean> = mapOf(
    LocationPermissionRequired.WifiState to context.isGranted(LocationPermissionRequired.WifiState),
    LocationPermissionRequired.AccessFine to context.isGranted(LocationPermissionRequired.AccessFine),
    LocationPermissionRequired.Coarse to context.isGranted(LocationPermissionRequired.Coarse),
)

private fun Context.isGranted(permission: LocationPermissionRequired): Boolean =
    ContextCompat.checkSelfPermission(this, permission.value) ==
            PackageManager.PERMISSION_GRANTED