/*
package com.splanes.grocery.ui.component.location.request

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.location.LocationPermissionRequired

@Composable
fun LocationPermissionsRequester() {
    var anySideEffect by remember { mutableStateOf(false) }

    when {
        Permissions.currentStateOf(Permissions.Permission.Location.toTypedArray()). ->
        else -> {
            val request = rememberLauncherForActivityResult(
                contract = RequestMultiplePermissions(),
            ) { resultMap ->
                when {
                    resultMap.values.all { granted -> granted } -> {
                        isPermissionSideEffectLaunched = true
                    }
                    else -> {
                        resultMap.keys
                            .filter { permission -> resultMap[permission] ?: false }
                            .map { permission -> LocationPermissionRequired.map(permission) }
                            .run(onPermissionDenied)
                    }
                }
            }

            permissions.keys
                .filter { permissionRequired -> permissions[permissionRequired] ?: false }
                .map { permissionRequired ->  permissionRequired.value }
                .run { request.launch(toTypedArray()) }
        }
    }
}*/
