package com.android.hospital.data.api.domain

import com.android.hospital.data.domain.UserGender
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
	val type: UserType,
	val name: String,
	val paternal: String,
	val maternal: String,
	val gender: UserGender,
	val personKey: String? = null,
	val speciality: String? = null,
	val license: Int? = null,
	val isActive: Boolean? = null
)
