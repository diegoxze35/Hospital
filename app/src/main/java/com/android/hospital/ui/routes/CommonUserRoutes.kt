package com.android.hospital.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.MedicalInformation
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Minimize
import androidx.compose.material.icons.outlined.MoreTime
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.hospital.R
import com.android.hospital.data.domain.medicalappointment.DoctorMedicalAppointment
import com.android.hospital.data.domain.medicalappointment.PatientMedicalAppointment
import com.android.hospital.data.domain.user.DoctorUser
import com.android.hospital.data.domain.user.PatientUser
import com.android.hospital.data.domain.user.ReceptionistUser
import com.android.hospital.ui.components.DoctorMedicalCiteCard
import com.android.hospital.ui.components.PatientMedicalCiteCard
import com.android.hospital.ui.components.SelectionDropDownMenu
import com.android.hospital.ui.domain.CiteStatus
import com.android.hospital.ui.screen.MainUserScreen
import com.android.hospital.ui.screen.Screen
import com.android.hospital.ui.viewmodel.UserViewModel


fun NavGraphBuilder.commonUserRoutes(
	navController: NavController,
	userViewModel: UserViewModel,
) {
	composable(route = Screen.UserMainScreen.route) {
		userViewModel.currentUser?.let { user ->
			val userOptions: List<String>
			val iconsOptions: List<ImageVector>
			val onClickOptions: List<() -> Unit>
			when (user) {
				is DoctorUser -> {
					userOptions = stringArrayResource(id = R.array.doctor_options).toList()
					iconsOptions = with(Icons.Outlined) {
						listOf(MedicalInformation, MenuBook)
					}
					onClickOptions = listOf(
						{
							userViewModel.getAllMedicalAppointmentsOfUser(statusId = 2)
							navController.navigate(Screen.MedicalCitesScreen.route)
						},
						{
							navController.navigate(Screen.PrescriptionScreen.route)
						}
					)
				}
				
				is PatientUser -> {
					userOptions =
						stringArrayResource(id = R.array.patient_options).toList()
					iconsOptions = with(Icons.Outlined) {
						listOf(MedicalInformation, MoreTime)
					}
					onClickOptions = listOf(
						{
							userViewModel.getAllMedicalAppointmentsOfUser(statusId = 2)
							navController.navigate(Screen.MedicalCitesScreen.route)
						},
						{
							userViewModel.getAllMedicalSpecialities()
							navController.navigate(Screen.AllMedicalSpecialitiesScreen.route)
						}
					)
				}
				
				is ReceptionistUser -> {
					userOptions = stringArrayResource(id = R.array.receptionist_options).toList()
					iconsOptions = with(Icons.Outlined) {
						listOf(Minimize)
					}
					onClickOptions = listOf {
						userViewModel.getAllPatientsAndDoctors()
						navController.navigate(Screen.AllUsersScreen.route)
					}
				}
			}
			MainUserScreen(
				modifier = Modifier.fillMaxSize(),
				userOptions = userOptions,
				icons = iconsOptions,
				onClickOptions = onClickOptions
			)
		}
	}
	
	composable(route = Screen.MedicalCitesScreen.route) {
		val cites by userViewModel.currentMedicalAppointments.collectAsState()
		Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
			var currentCiteStatus by rememberSaveable {
				mutableStateOf(CiteStatus.Next)
			}
			SelectionDropDownMenu(
				modifier = Modifier.align(Alignment.CenterHorizontally),
				items = CiteStatus.values(),
				textMenu = stringResource(id = currentCiteStatus.text),
				textItem = {
					Text(text = stringResource(id = it.text))
				},
				leadingIcon = {
					Icon(
						imageVector = Icons.Outlined.Circle,
						contentDescription = stringResource(id = it.text)
					)
				},
				onClickItem = {
					userViewModel.getAllMedicalAppointmentsOfUser(statusId = it.ordinal + 1)
					currentCiteStatus = it
				}
			)
			if (cites.isEmpty()) {
				Text(
					modifier = Modifier.align(Alignment.CenterHorizontally),
					text = stringResource(
						R.string.empty_cites_message,
						stringResource(id = currentCiteStatus.text)
					)
				)
			} else {
				var currentSelectedCiteId: Int? by rememberSaveable {
					mutableStateOf(null)
				}
				LazyColumn(
					modifier = Modifier.fillMaxSize(),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					items(cites) {
						when (it) {
							is DoctorMedicalAppointment -> DoctorMedicalCiteCard(it)
							is PatientMedicalAppointment -> PatientMedicalCiteCard(
								cite = it,
								status = currentCiteStatus,
								onModifyCite = {
									userViewModel.getAllRoomNumbers()
									navController.navigate(
										"${Screen.HourOfMedicalCitesScreen.route}/${it.doctorId}/${it.doctorFullName}?citeId=${it.citeId}"
									)
								},
								onCancelCite = { citeId ->
									currentSelectedCiteId = citeId
								}
							)
						}
					}
				}
				currentSelectedCiteId?.let {
					AlertDialog(
						text = {
							Text(text = stringResource(R.string.cancel_medical_cite))
						},
						onDismissRequest = { currentSelectedCiteId = null },
						confirmButton = {
							Button(onClick = {
								userViewModel.cancelCite(it)
								currentSelectedCiteId = null
							}) {
								Text(text = stringResource(id = R.string.confirm))
							}
						},
						dismissButton = {
							Button(onClick = { currentSelectedCiteId = null }) {
								Text(text = stringResource(id = R.string.cancel))
							}
						}
					)
				}
			}
		}
	}
}