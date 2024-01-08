package com.android.hospital.ui.routes

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.hospital.data.domain.user.DoctorUser
import com.android.hospital.data.domain.user.PatientUser
import com.android.hospital.data.domain.user.User
import com.android.hospital.ui.domain.InputField
import com.android.hospital.ui.screen.Screen
import com.android.hospital.ui.screen.login.LoginScreen
import com.android.hospital.ui.state.AuthUserState
import com.android.hospital.ui.viewmodel.LoginViewModel

fun NavGraphBuilder.loginRoutes(navController: NavController, onLogin: (User) -> Unit) {
	composable(route = Screen.LoginScreen.route) {
		val loginViewModel = hiltViewModel<LoginViewModel>()
		val state by loginViewModel.loginState.collectAsState(AuthUserState.Initial)
		LoginScreen(
			credentials = loginViewModel.inputState,
			onUsernameChange = {
				loginViewModel.currentField = InputField.USERNAME
				loginViewModel.field = it
			},
			onPasswordChange = {
				loginViewModel.currentField = InputField.PASSWORD
				loginViewModel.field = it
			},
			canTryLogin = loginViewModel.canTryLogin,
			isLoading = loginViewModel.isLoading,
			onClickLogin = loginViewModel::login,
			authState = state
		) { user ->
			if ((user is PatientUser && !user.isActive) || (user is DoctorUser && !user.isActive)) {
				navController.navigate(Screen.UserNotActiveScreen.route)
			} else {
				navController.navigate(
					route = Screen.UserMainScreen.route
				) {
					launchSingleTop = true
					popUpTo(route = Screen.LoginScreen.route) {
						inclusive = true
					}
				}
				onLogin(user)
			}
		}
	}
}