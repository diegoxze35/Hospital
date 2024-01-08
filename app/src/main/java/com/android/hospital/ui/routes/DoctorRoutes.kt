package com.android.hospital.ui.routes

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.hospital.ui.screen.Screen
import com.android.hospital.ui.screen.doctorscreens.PrescriptionScreen
import com.android.hospital.ui.viewmodel.UserViewModel

fun NavGraphBuilder.doctorRoutes(navController: NavController, userViewModel: UserViewModel) {
	composable(route = Screen.PrescriptionScreen.route) {
		val prescriptions by userViewModel.currentPrescription.collectAsState()
		PrescriptionScreen(
			prescriptions = prescriptions,
			onSendPatientName = userViewModel::getPrescriptionsOfPatient
		)
	}
}