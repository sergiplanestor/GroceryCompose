package com.splanes.grocery.data.feature.user.mapper

import com.splanes.grocery.data.feature.user.entity.UserDto
import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.utils.primitives.orEmpty
import javax.inject.Inject

class UserDtoMapper @Inject constructor() {

    fun map(dto: UserDto): User = with(dto) {
        User(
            id = id.orEmpty(),
            name = name.orEmpty(),
            email = email.orEmpty(),
            createdOn = createdOn.orEmpty()
        )
    }

    fun map(user: User): UserDto = with(user) {
        UserDto(
            id = id,
            name = name,
            email = email,
            createdOn = createdOn
        )
    }
}