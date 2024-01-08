package com.android.hospital.data.domain

import com.android.hospital.data.api.domain.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class CreatedUserResponse(
	val username: String,
	val newUser: UserResponse
)
