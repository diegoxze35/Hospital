package com.android.hospital.ui.viewmodel.state

import com.android.hospital.data.domain.CreatedUserResponse


sealed class AddUserUIState {
	object AddingUserUI : AddUserUIState()
	object LoadingAddedUserUI : AddUserUIState()
	data class UserCreatedUI(val response: CreatedUserResponse) : AddUserUIState()
}
