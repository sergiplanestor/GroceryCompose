package com.splanes.grocery.domain.feature.user.model

sealed class UserSearch {
    data class ById(val id: String) : UserSearch()
    data class ByName(val username: String) : UserSearch()
    data class ByEmail(val email: String) : UserSearch()

    fun match(user: User): Boolean =
        when (this) {
            is ByEmail -> user.email == email
            is ById -> user.id == id
            is ByName -> user.name == username
        }

    companion object {
        fun byId(user: User): UserSearch = ById(user.id)
        fun byName(user: User): UserSearch = ByName(user.name)
        fun byEmail(user: User): UserSearch = ByEmail(user.email)
    }
}
