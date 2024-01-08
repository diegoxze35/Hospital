package com.android.hospital.ui.domain

import com.android.hospital.data.domain.UpdatableUser
import kotlinx.serialization.Serializable

@Serializable
data class UpdateIsActiveUserRequest(
	val userId: Int,
	val userType: UpdatableUser,
	val newIsActive: Boolean
)
