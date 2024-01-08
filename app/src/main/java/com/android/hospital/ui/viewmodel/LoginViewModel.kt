package com.android.hospital.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.hospital.ui.domain.InputField
import com.android.hospital.data.domain.UserCredentials
import com.android.hospital.data.repository.domain.AuthResult
import com.android.hospital.data.usecase.LoginUseCase
import com.android.hospital.toDomain
import com.android.hospital.ui.state.AuthUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
	var currentField = InputField.USERNAME
	var field = String()
		set(value) {
			_loginState.value = AuthUserState.Initial
			inputState = when (currentField) {
				InputField.USERNAME -> inputState.copy(username = value.trim().uppercase())
				InputField.PASSWORD -> inputState.copy(password = value)
			}
		}
	var inputState by mutableStateOf(UserCredentials())
		private set
	var isLoading by mutableStateOf(false)
		private set
	val canTryLogin: Boolean
		get() = with(inputState) { username.isNotBlank() && password.isNotEmpty() }
	
	private val _loginState: MutableStateFlow<AuthUserState> =
		MutableStateFlow(AuthUserState.Initial)
	val loginState: Flow<AuthUserState> = _loginState.asStateFlow()
	
	fun login() {
		isLoading = true
		viewModelScope.launch {
			_loginState.value = when (val result = loginUseCase(inputState)) {
				is AuthResult.Authorized -> AuthUserState.AuthenticationSuccess(
					user = result.user.toDomain()
				)
				
				is AuthResult.AuthorizedWithIncorrectPassword -> AuthUserState.AuthenticationWithIncorrectPassword(
					user = result.user
				)
				
				AuthResult.UnknownUser -> AuthUserState.UnknownUser
			}
			isLoading = false
		}
	}
}