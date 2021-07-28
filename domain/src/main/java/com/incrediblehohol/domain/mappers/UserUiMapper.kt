package com.incrediblehohol.domain.mappers

import com.incrediblehohol.data.remote.utils.UserDTO
import com.incrediblehohol.domain.contracts.IMapper
import com.incrediblehohol.domain.models.UserUI
import javax.inject.Inject

class UserUiMapper @Inject constructor() : IMapper<UserDTO, UserUI> {
    override fun map(input: UserDTO): UserUI {
        return UserUI(
            id = input.id,
            email = input.email ?: "",
            firstName = input.firstName ?: "",
            lastName = input.lastName ?: "",
            avatar = input.avatar
        )
    }
}