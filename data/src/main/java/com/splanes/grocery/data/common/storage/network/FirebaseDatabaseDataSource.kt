package com.splanes.grocery.data.common.storage.network

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.splanes.grocery.data.feature.user.datasource.UserNetworkDataSource
import com.splanes.grocery.data.feature.user.entity.UserDto
import com.splanes.grocery.data.utils.firebase.database.fetch
import com.splanes.grocery.data.utils.firebase.database.fromJson
import com.splanes.grocery.data.utils.firebase.task.completeWithSuccess
import com.splanes.grocery.domain.feature.user.model.UserSearch
import javax.inject.Inject

class FirebaseDatabaseDataSource @Inject constructor(
    private val db: FirebaseDatabase
) : UserNetworkDataSource {

    private fun ref(path: String = USER): DatabaseReference =
        db.getReference(path)

    override suspend fun insertOrUpdate(id: String, data: String): Boolean =
        completeWithSuccess { ref().setValue(id, data) }

    override suspend fun search(search: UserSearch): UserDto? =
        if (search is UserSearch.ById) {
            ref().child(search.id).fetch { data -> data.fromJson() }
        } else {
            ref().fetch { usersSnapshot ->
                usersSnapshot.children.find { search.match(it.fromJson()) }?.fromJson()
            }
        }

    companion object {
        private const val USER = "db/usr/"
    }
}