package com.android.hospital.data.api.domain

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticatedUser(
	val token: String?,
	val user: UserResponse?
)