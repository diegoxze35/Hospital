package com.android.hospital.data.domain

sealed class UpdateUserStateResponse {
	object Success : UpdateUserStateResponse()
	data class CannotUpdateUserState(
		val pendingAppointments: Int
	) : UpdateUserStateResponse()
}
