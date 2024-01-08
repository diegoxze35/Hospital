package com.android.hospital.data.domain

import com.android.hospital.data.domain.user.User

sealed class LoginResult(open val user: User?) {
	data class LoginSuccess(override val user: User) : LoginResult(user)
	data class LoginIncorrect(override val user: User) : LoginResult(user)
	object LoginLoading : LoginResult(user = null)
	object LoginNotFound : LoginResult(user = null)
}
