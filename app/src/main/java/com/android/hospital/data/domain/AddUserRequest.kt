package com.android.hospital.data.domain

import com.android.hospital.data.domain.user.User
import kotlinx.serialization.Serializable

@Serializable
data class AddUserRequest(
	val newUser: User,
	val password: String
)