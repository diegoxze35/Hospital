package com.android.hospital.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.hospital.R
import com.android.hospital.data.domain.EditMedicalCiteRequest
import com.android.hospital.data.domain.MedicCiteRequest
import com.android.hospital.ui.screen.Screen
import com.android.hospital.ui.screen.patientscreens.MedicalAppointmentsTimeScreen
import com.android.hospital.ui.screen.patientscreens.MedicalCitesScreen
import com.android.hospital.ui.viewmodel.UserViewModel
import com.android.hospital.ui.viewmodel.state.ScheduleMedicCiteState

fun NavGraphBuilder.patientRoutes(
	navController: NavController,
	userViewModel: UserViewModel,
) {
	
	composable(route = Screen.AllMedicalSpecialitiesScreen.route) {
		if (userViewModel.allMedicalSpecialities.isEmpty()) {
			Box(Modifier.fillMaxSize()) {
				CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
			}
		} else {
			MedicalCitesScreen(
				medicalCites = userViewModel.allMedicalSpecialities,
				onSpecialitySelected = userViewModel::getAllDoctorsBySpeciality,
				loadingDoctors = userViewModel.doctorsBySpeciality.isEmpty(),
				doctorsOfSpeciality = userViewModel.doctorsBySpeciality,
				onClickDoctor = {
					userViewModel.getAllRoomNumbers()
					navController.navigate(
						"${Screen.HourOfMedicalCitesScreen.route}/${it.doctorId}/${it.fullName}"
					)
				}
			)
		}
	}
	
	composable(
		route = "${Screen.HourOfMedicalCitesScreen.route}/{doctorId}/{doctorFullName}?citeId={citeId}",
		arguments = listOf(
			navArgument("doctorId") { NavType.StringType },
			navArgument("doctorFullName") { NavType.StringType },
			navArgument(name = "citeId") {
				nullable = true
				defaultValue = null
				NavType.StringType
			}
		)
	) { entry ->
		val doctorId = checkNotNull(entry.arguments?.getString("doctorId")).toInt()
		val doctorFullName = checkNotNull(entry.arguments?.getString("doctorFullName"))
		val citeId = entry.arguments?.getString("citeId")?.toInt()
		if (userViewModel.allRoomsNumbers.isEmpty()) {
			Box(modifier = Modifier.fillMaxSize()) {
				CircularProgressIndicator(
					modifier = Modifier.align(Alignment.Center)
				)
			}
		} else {
			Column(
				modifier = Modifier.fillMaxSize(),
				verticalArrangement = Arrangement.Center
			) {
				Text(
					modifier = Modifier.fillMaxWidth(),
					style = MaterialTheme.typography.titleLarge,
					text = stringResource(id = R.string.cites_with_doctor, doctorFullName),
					textAlign = TextAlign.Center
				)
				
				val availableHours = remember { mutableStateListOf<String>() }
				MedicalAppointmentsTimeScreen(
					consultingRooms = userViewModel.allRoomsNumbers,
					withDoctor = doctorFullName,
					onDateSelect = { roomNumber, date ->
						availableHours.clear()
						userViewModel.getAvailableTimesForMedicalCites(
							doctorId = doctorId,
							roomNumber = roomNumber,
							date = date
						) { newHours ->
							availableHours.addAll(newHours)
						}
					},
					availableHours = availableHours
				) { roomNumber, date ->
					citeId?.let {
						userViewModel.updateRegisterCite(
							EditMedicalCiteRequest(
								citeId = it,
								newDate = date,
								newRoomNumber = roomNumber
							)
						)
						navController.navigate(Screen.EditMedicalCiteSuccessScreen.route) {
							popUpTo(Screen.MedicalCitesScreen.route) {
								inclusive = true
								saveState = false
							}
						}
					} ?: kotlin.run {
						userViewModel.registerMedicCite(
							MedicCiteRequest(
								doctorId,
								date,
								roomNumber
							)
						)
						navController.navigate(Screen.ConfirmationMedicCiteScreen.route) {
							popUpTo(Screen.AllMedicalSpecialitiesScreen.route) {
								inclusive = true
								saveState = false
							}
						}
					}
				}
			}
		}
	}
	
	composable(route = Screen.ConfirmationMedicCiteScreen.route) {
		val state by userViewModel.scheduleMedicCiteState.collectAsState()
		Box(modifier = Modifier.fillMaxSize()) {
			val center = Modifier.align(Alignment.Center)
			when (val result = state) {
				ScheduleMedicCiteState.Loading -> CircularProgressIndicator(
					modifier = center
				)
				
				is ScheduleMedicCiteState.Success -> OutlinedCard(
					modifier = center.fillMaxWidth(fraction = 0.8f),
					elevation = CardDefaults.elevatedCardElevation(
						defaultElevation = 8.dp
					),
					colors = CardDefaults.cardColors(
						containerColor = MaterialTheme.colorScheme.tertiaryContainer,
						contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
					)
				) {
					Text(
						modifier = Modifier.fillMaxWidth(),
						text = stringResource(R.string.cite_schedule_correctly),
						style = MaterialTheme.typography.titleLarge,
						textAlign = TextAlign.Center
					)
					Spacer(modifier = Modifier.height(IntrinsicSize.Min))
					Text(
						modifier = Modifier.padding(all = 24.dp),
						text = stringResource(
							R.string.shcedule_medic_cite,
							result.medicCite.citeId,
							result.medicCite.specialityName,
							result.medicCite.dateTime,
							result.medicCite.roomNumber
						),
						style = MaterialTheme.typography.bodyLarge
					)
				}
			}
		}
	}
	
	composable(Screen.EditMedicalCiteSuccessScreen.route) {
		Box(modifier = Modifier.fillMaxSize()) {
			Text(
				modifier = Modifier.align(Alignment.Center),
				text = stringResource(R.string.medical_cite_updated_correctly),
				style = MaterialTheme.typography.titleLarge,
				fontWeight = FontWeight.ExtraBold
			)
		}
	}
}