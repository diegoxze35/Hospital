package com.android.hospital.ui.state

import com.android.hospital.data.api.domain.UserResponse
import com.android.hospital.data.domain.user.User

sealed class AuthUserState {
	object Initial : AuthUserState()
	object UnknownUser : AuthUserState()
	data class AuthenticationWithIncorrectPassword(val user: UserResponse) : AuthUserState()
	data class AuthenticationSuccess(val user: User) : AuthUserState()
}
