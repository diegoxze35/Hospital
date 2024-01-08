package com.android.hospital.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.datastore.preferences.core.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.hospital.R
import com.android.hospital.TOKEN_KEY
import com.android.hospital.data.domain.user.ReceptionistUser
import com.android.hospital.dataStore
import com.android.hospital.ui.routes.commonUserRoutes
import com.android.hospital.ui.routes.doctorRoutes
import com.android.hospital.ui.routes.loginRoutes
import com.android.hospital.ui.routes.patientRoutes
import com.android.hospital.ui.routes.receptionistRoutes
import com.android.hospital.ui.screen.Screen
import com.android.hospital.ui.screen.appbar.TopAppBarMainUserScreen
import com.android.hospital.ui.theme.HospitalTheme
import com.android.hospital.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@OptIn(ExperimentalMaterial3Api::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val userViewModel = hiltViewModel<UserViewModel>()
			HospitalTheme(userTheme = userViewModel.currentUser) {
				val navController = rememberNavController()
				Scaffold(topBar = {
					userViewModel.currentUser?.let { user ->
						val context = LocalContext.current
						TopAppBarMainUserScreen(user = user) {
							withContext(Dispatchers.IO) {
								context.dataStore.edit {
									it.remove(TOKEN_KEY)
								}
							}
							withContext(Dispatchers.Main) {
								navController.navigate(route = Screen.LoginScreen.route) {
									launchSingleTop = true
									popUpTo(Screen.UserMainScreen.route) {
										inclusive = true
									}
								}
								userViewModel.currentUser = null
							}
						}
					}
				}, floatingActionButton = {
					val route by navController.currentBackStackEntryAsState()
					if (route?.destination?.route == Screen.UserMainScreen.route && userViewModel.currentUser is ReceptionistUser) {
						ExtendedFloatingActionButton(
							text = { Text(text = stringResource(R.string.add_user)) },
							icon = {
								Icon(
									imageVector = Icons.Default.PersonAdd,
									contentDescription = stringResource(R.string.add_user)
								)
							},
							onClick = {
								userViewModel.getAllMedicalSpecialities()
								navController.navigate(Screen.AddUserScreen.route)
							}
						)
					}
				}) { innerPadding ->
					val paddingValues = PaddingValues(
						top = innerPadding.calculateTopPadding(),
						start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
						end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
						bottom = innerPadding.calculateBottomPadding()
					)
					NavHost(
						modifier = Modifier.padding(paddingValues),
						navController = navController,
						startDestination = Screen.LoginScreen.route
					) {
						loginRoutes(
							navController,
							onLogin = {
								userViewModel.currentUser = it
							}
						)
						commonUserRoutes(navController, userViewModel)
						patientRoutes(navController, userViewModel)
						doctorRoutes(navController, userViewModel)
						receptionistRoutes(/*navController, */userViewModel)
					}
				}
			}
		}
	}
}
