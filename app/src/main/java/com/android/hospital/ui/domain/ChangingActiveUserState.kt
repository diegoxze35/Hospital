package com.android.hospital.ui.domain

sealed class ChangingActiveUserState {
	object None : ChangingActiveUserState()
	data class OnChangeRequest(val updateIsActiveUserRequest: UpdateIsActiveUserRequest) :
		ChangingActiveUserState()
}
