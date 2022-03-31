package com.splanes.grocery.data.common.storage.local

import android.content.SharedPreferences
import com.splanes.grocery.data.feature.user.datasource.UserLocalDataSource
import com.splanes.grocery.data.feature.user.entity.UserDto
import com.splanes.grocery.data.utils.preferences.getJsonOrNull
import com.splanes.grocery.data.utils.preferences.putJson
import com.splanes.grocery.domain.feature.user.model.UserSearch
import com.splanes.grocery.utils.logger.utils.throwError
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val preferences: SharedPreferences
) : UserLocalDataSource {

    override suspend fun fetch(): UserDto? =
        preferences.getJsonOrNull(CURRENT)

    override suspend fun insertOrUpdate(data: UserDto): Boolean =
        evaluate { preferences.putJson(CURRENT, data) }

    override suspend fun search(search: UserSearch): UserDto? {
        TODO("Not yet implemented")
    }

    private suspend fun evaluate(
        onError: suspend (Throwable) -> Boolean = { throwError { it } },
        block: suspend () -> Unit
    ): Boolean =
        runCatching {
            block()
            true
        }.getOrElse {
            onError(it)
        }

    companion object {
        private const val CURRENT = "usr.current"
        private const val RECENTLY_SEARCHED = "usr.recent"
    }
}