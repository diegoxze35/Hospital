package com.android.hospital.data.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
	val username: String = String(),
	val password: String = String()
)
