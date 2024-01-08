package com.android.hospital.data.repository.domain

import com.android.hospital.data.api.domain.UserResponse

sealed class AuthResult {
	data class Authorized(val user: UserResponse, val token: String) : AuthResult()
	data class AuthorizedWithIncorrectPassword(val user: UserResponse) : AuthResult()
	object UnknownUser : AuthResult()
}
