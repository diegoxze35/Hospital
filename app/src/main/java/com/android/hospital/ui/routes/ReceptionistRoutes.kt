package com.android.hospital.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Dangerous
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
/*import androidx.navigation.NavController*/
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.hospital.R
import com.android.hospital.data.domain.EmitTicketRequest
import com.android.hospital.data.domain.UpdatableUser
import com.android.hospital.ui.domain.UpdateIsActiveUserRequest
import com.android.hospital.ui.screen.Screen
import com.android.hospital.ui.screen.receptionistscreens.AddUserScreen
import com.android.hospital.ui.viewmodel.UserViewModel

fun NavGraphBuilder.receptionistRoutes(/*navController: NavController, */userViewModel: UserViewModel) {
	composable(route = Screen.AddUserScreen.route) {
		if (userViewModel.allMedicalSpecialities.isEmpty()) {
			Box(Modifier.fillMaxSize()) {
				CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
			}
		} else {
			val state by userViewModel.specialitiesState.collectAsState()
			AddUserScreen(
				medicalSpecialityNames = userViewModel.allMedicalSpecialities.map { it.name },
				onAdded = userViewModel::addUser,
				onAddNewUser = userViewModel::addNewUser,
				addUserUIState = state,
			)
		}
	}
	composable(Screen.AllUsersScreen.route) {
		val allUsers by userViewModel.allUsers.collectAsState()
		var selectedUserFullName by remember {
			mutableStateOf(String())
		}
		var changingUserActiveState: UpdateIsActiveUserRequest? by remember { mutableStateOf(null) }
		LazyColumn(
			modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			items(allUsers) {
				Card(
					modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
						containerColor = if (it.isActive) MaterialTheme.colorScheme.secondaryContainer
						else MaterialTheme.colorScheme.errorContainer,
						contentColor = if (it.isActive) MaterialTheme.colorScheme.onSecondaryContainer
						else MaterialTheme.colorScheme.onErrorContainer
					)
				) {
					Row(
						horizontalArrangement = Arrangement.Center,
						modifier = Modifier.padding(all = 8.dp)
					) {
						Column(modifier = Modifier.weight(1f)) {
							Text(text = it.fullName, style = MaterialTheme.typography.titleLarge)
							Text(
								text = stringResource(
									id = when (it.userType) {
										UpdatableUser.Patient -> R.string.patient
										UpdatableUser.Doctor -> R.string.doctor
									}
								), style = MaterialTheme.typography.titleMedium
							)
						}
						Button(
							onClick = {
								selectedUserFullName = it.fullName
								changingUserActiveState = UpdateIsActiveUserRequest(
									userId = it.id,
									userType = it.userType,
									newIsActive = !it.isActive
								)
							},
							modifier = Modifier.weight(0.5f),
							colors = ButtonDefaults.buttonColors(
								containerColor = if (it.isActive) MaterialTheme.colorScheme.errorContainer
								else MaterialTheme.colorScheme.secondaryContainer,
								contentColor = if (it.isActive) MaterialTheme.colorScheme.onErrorContainer
								else MaterialTheme.colorScheme.onSecondaryContainer
							)
						) {
							Icon(
								imageVector = if (it.isActive) Icons.Outlined.Dangerous
								else Icons.Outlined.Check, contentDescription = null
							)
							Text(
								text = if (it.isActive) stringResource(R.string.to_down) else stringResource(
									R.string.to_up
								)
							)
						}
					}
				}
			}
		}
		changingUserActiveState?.let {
			AlertDialog(
				text = {
					Text(
						text = stringResource(
							id = if (it.newIsActive) R.string.to_up
							else R.string.to_down
						) + " $selectedUserFullName"
					)
				},
				onDismissRequest = { changingUserActiveState = null },
				confirmButton = {
					Button(
						onClick = {
							userViewModel.updateActiveUser(it)
							changingUserActiveState = null
						}
					) {
						Text(
							text = stringResource(id = R.string.confirm)
						)
					}
				},
				dismissButton = {
					Button(onClick = { changingUserActiveState = null }) {
						Text(text = stringResource(id = R.string.cancel))
					}
				}
			)
		}
		userViewModel.currentFail?.let {
			AlertDialog(
				onDismissRequest = {
					userViewModel.currentFail = null
				},
				text = {
					Text(
						text = stringResource(
							id = R.string.cannot_update,
							it.pendingAppointments
						)
					)
				},
				confirmButton = {
					Button(onClick = { userViewModel.currentFail = null }) {
						Text(text = stringResource(id = R.string.confirm))
					}
				}
			)
		}
	}
	
	composable(Screen.TicketsScreen.route) {
		val allTickets by userViewModel.currentTickets.collectAsState()
		LazyColumn(
			modifier = Modifier.fillMaxSize(),
			contentPadding = PaddingValues(all = 8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(allTickets) {
				Card(modifier = Modifier.fillMaxWidth()) {
					Text(
						text = it.patientFullName,
						style = MaterialTheme.typography.titleLarge,
						fontWeight = FontWeight.ExtraBold
					)
					Row(horizontalArrangement = Arrangement.Center) {
						Icon(
							imageVector = Icons.Outlined.AttachMoney,
							contentDescription = it.total.toString()
						)
						Text(text = it.total.toString())
					}
					LazyRow(
						modifier = Modifier.padding(all = 16.dp),
						horizontalArrangement = Arrangement.spacedBy(16.dp)
					) {
						items(it.tickets) { ticket ->
							Card(
								colors = CardDefaults.cardColors(
									containerColor = MaterialTheme.colorScheme.tertiaryContainer,
									contentColor = MaterialTheme.colorScheme.onTertiaryContainer
								),
								elevation = CardDefaults.cardElevation(
									defaultElevation = 8.dp
								)
							) {
								Text(text = ticket.concept)
								Text(text = ticket.amount.toString())
							}
						}
					}
					Button(
						onClick = {
							userViewModel.emitTickets(
								EmitTicketRequest(it.patientId)
							)
						},
						modifier = Modifier.fillMaxWidth()
					) {
						Text(text = stringResource(id = R.string.emit))
					}
				}
			}
		}
	}
}