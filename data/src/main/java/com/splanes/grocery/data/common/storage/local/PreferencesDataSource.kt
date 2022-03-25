package com.splanes.grocery.data.common.storage.local

import android.content.SharedPreferences
import com.splanes.grocery.data.feature.user.datasource.UserLocalDataSource
import com.splanes.grocery.data.feature.user.entity.UserDto
import com.splanes.grocery.data.utils.preferences.getJsonOrNull
import com.splanes.grocery.data.utils.preferences.putJson
import com.splanes.grocery.domain.feature.user.model.UserSearch
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val preferences: SharedPreferences
) : UserLocalDataSource {

    override suspend fun fetchCurrent(): UserDto? =
        preferences.getJsonOrNull(CURRENT)

    override suspend fun insertOrUpdateCurrent(data: UserDto) {
        preferences.putJson(CURRENT, data)
    }

    override suspend fun search(search: UserSearch): UserDto? {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrUpdate(data: UserDto) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val CURRENT = "usr.current"
        private const val RECENTLY_SEARCHED = "usr.recent"
    }
}