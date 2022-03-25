package com.splanes.grocery.domain.feature.user.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val createdOn: Long
)
