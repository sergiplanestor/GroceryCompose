package com.splanes.grocery.ui.component.location.request

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.splanes.grocery.ui.component.location.request.Permissions.State.Denied
import com.splanes.grocery.ui.component.location.request.Permissions.State.Granted

object Permissions {

    enum class Permission(val value: String) {
        LocationAccessFine(value = Manifest.permission.ACCESS_FINE_LOCATION),
        LocationAccessCoarse(value = Manifest.permission.ACCESS_COARSE_LOCATION), ;

        companion object
    }

    enum class State {
        Granted,
        Denied;

        fun granted(): Boolean = this == Granted
    }

    fun stateOf(value: Boolean?): State = if (value == true) Granted else Denied

    data class Result(val permissions: Map<Permission, State>)

    fun resultOf(map: Map<String, State>): Result =
        map.mapKeys { entry -> Permission.find(entry.key) }
            .mapValues { entry -> stateOf(entry.value.granted()) }
            .let { data -> Result(data) }

    fun currentStateOf(context: Context, vararg permissions: Permission): Result =
        Result(permissions = permissions.associateWith { permission -> stateOf(context.isGranted(permission)) })

    @Composable
    fun currentStateOf(vararg permission: Permission): Result =
        currentStateOf(context = LocalContext.current, *permission)
}

val Permissions.Permission.Companion.Location: List<Permissions.Permission> by lazy {
    listOf(
        Permissions.Permission.LocationAccessFine,
        Permissions.Permission.LocationAccessCoarse
    )
}

fun Permissions.Permission.Companion.find(permission: String): Permissions.Permission =
    Permissions.Permission.values().first { it.value == permission }

fun Permissions.Result.granted(): Boolean = permissions.values.all { state -> state.granted() }

fun Context.isGranted(permission: Permissions.Permission): Boolean =
    ContextCompat.checkSelfPermission(this, permission.value) ==
            PackageManager.PERMISSION_GRANTED