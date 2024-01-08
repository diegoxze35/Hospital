package com.android.hospital.ui.screen.receptionistscreens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.android.hospital.R
import com.android.hospital.data.domain.AddUserRequest
import com.android.hospital.data.domain.CreatedUserResponse
import com.android.hospital.toDomain
import com.android.hospital.ui.components.SelectionDropDownMenu
import com.android.hospital.ui.screen.receptionistscreens.domain.AddUserState
import com.android.hospital.ui.screen.receptionistscreens.domain.UserGenderUI
import com.android.hospital.ui.screen.receptionistscreens.domain.UserTypeUI
import com.android.hospital.ui.viewmodel.state.AddUserUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserScreen(
	medicalSpecialityNames: List<String>,
	modifier: Modifier = Modifier,
	onAdded: (AddUserRequest) -> Unit,
	onAddNewUser: () -> Unit,
	addUserUIState: AddUserUIState,
) {
	when (addUserUIState) {
		
		AddUserUIState.LoadingAddedUserUI -> {
			Column(
				Modifier.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(text = stringResource(R.string.adding_user))
				LinearProgressIndicator()
			}
		}
		
		is AddUserUIState.UserCreatedUI -> {
			Column(
				modifier = Modifier.fillMaxSize(),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center
			) {
				val infiniteTransition =
					rememberInfiniteTransition(label = "Infinite Rotate Animation")
				val rotation by infiniteTransition.animateFloat(
					initialValue = -10f,
					targetValue = 10f,
					animationSpec = infiniteRepeatable(
						animation = tween(durationMillis = 500),
						repeatMode = RepeatMode.Reverse
					),
					label = "Infinite Rotation"
				)
				Icon(
					imageVector = Icons.Default.AddReaction,
					contentDescription = stringResource(R.string.user_adding_correctly_to_system),
					modifier = Modifier
						.size(dimensionResource(id = R.dimen.icon_button_size))
						.rotate(rotation)
				)
				Spacer(modifier = Modifier.height(8.dp))
				
				TextButton(onClick = onAddNewUser) {
					Text(
						text = stringResource(R.string.add_other_user),
						style = MaterialTheme.typography.bodyMedium
					)
				}
				Spacer(modifier = Modifier.height(8.dp))
				NewUserDetails(addUserUIState.response)
			}
		}
		
		AddUserUIState.AddingUserUI -> {
			Column(
				modifier = modifier
					.fillMaxSize()
					.verticalScroll(state = rememberScrollState()),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.SpaceEvenly
			) {
				var addUserState by remember { mutableStateOf(AddUserState()) }
				val modifierWidth = Modifier.fillMaxWidth(fraction = 0.8f)
				TextField(
					modifier = modifierWidth,
					value = addUserState.name,
					onValueChange = { addUserState = addUserState.copy(name = it) },
					label = {
						Text(text = stringResource(R.string.name_of_new_user))
					},
					keyboardOptions = KeyboardOptions(
						imeAction = ImeAction.Next
					),
					singleLine = true
				)
				TextField(
					modifier = modifierWidth,
					value = addUserState.paternal,
					onValueChange = { addUserState = addUserState.copy(paternal = it) },
					label = {
						Text(text = stringResource(R.string.paternal_of_new_user))
					},
					keyboardOptions = KeyboardOptions(
						imeAction = ImeAction.Next
					),
					singleLine = true
				)
				TextField(
					modifier = modifierWidth,
					value = addUserState.maternal,
					onValueChange = { addUserState = addUserState.copy(maternal = it) },
					label = {
						Text(text = stringResource(R.string.maternal_of_new_user))
					},
					keyboardOptions = KeyboardOptions(
						imeAction = ImeAction.Next
					),
					singleLine = true
				)
				TextField(
					modifier = modifierWidth,
					value = addUserState.newPassword,
					onValueChange = { addUserState = addUserState.copy(newPassword = it) },
					label = {
						Text(text = stringResource(R.string.new_password))
					},
					keyboardOptions = KeyboardOptions(
						imeAction = when (addUserState.userTypeUI) {
							UserTypeUI.Receptionist -> ImeAction.Done
							else -> ImeAction.Next
						}
					),
					singleLine = true
				)
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceEvenly
				) {
					SelectionDropDownMenu(
						items = UserGenderUI.values(),
						textMenu = stringResource(id = addUserState.userGenderUI.textId),
						textItem = {
							Text(text = stringResource(id = it.textId))
						},
						leadingIcon = {
							Icon(imageVector = it.icon, contentDescription = null)
						},
						onClickItem = {
							addUserState = addUserState.copy(userGenderUI = it)
						}
					)
					SelectionDropDownMenu(
						items = UserTypeUI.values(),
						textMenu = stringResource(id = addUserState.userTypeUI.textId),
						textItem = {
							Text(text = stringResource(id = it.textId))
						},
						leadingIcon = {
							Icon(imageVector = it.icon, contentDescription = null)
						},
						onClickItem = {
							addUserState = addUserState.copy(userTypeUI = it)
						}
					)
				}
				when (addUserState.userTypeUI) {
					UserTypeUI.Patient -> {
						addUserState = addUserState.copy(
							doctorLicense = null,
							doctorSpeciality = null
						)
						TextField(
							modifier = modifierWidth,
							value = addUserState.personKeyPatient.orEmpty(),
							onValueChange = {
								addUserState = addUserState.copy(personKeyPatient = it)
							},
							label = { Text(text = stringResource(id = R.string.person_key)) },
							singleLine = true,
							keyboardOptions = KeyboardOptions(
								imeAction = ImeAction.Done,
								autoCorrect = false
							)
						)
					}
					
					UserTypeUI.Doctor -> {
						addUserState = addUserState.copy(
							personKeyPatient = null
						)
						TextField(
							modifier = modifierWidth,
							value = addUserState.doctorLicense?.toString().orEmpty(),
							onValueChange = {
								addUserState = addUserState.copy(doctorLicense = it.toIntOrNull())
							},
							label = { Text(text = stringResource(id = R.string.doctor_licence)) },
							singleLine = true,
							keyboardOptions = KeyboardOptions(
								imeAction = ImeAction.Done,
								keyboardType = KeyboardType.Number
							)
						)
						
						SelectionDropDownMenu(
							items = medicalSpecialityNames,
							textMenu = addUserState.doctorSpeciality
								?: stringResource(id = R.string.select_speciality),
							textItem = {
								Text(text = it)
							},
							leadingIcon = {
								Icon(imageVector = Icons.Default.List, contentDescription = it)
							},
							onClickItem = {
								addUserState = addUserState.copy(doctorSpeciality = it)
							}
						)
					}
					
					UserTypeUI.Receptionist -> {
						addUserState = addUserState.copy(
							personKeyPatient = null,
							doctorLicense = null,
							doctorSpeciality = null
						)
						Divider(modifier = modifierWidth)
					}
				}
				Button(
					onClick = {
						onAdded(addUserState.toDomain())
					},
					enabled = with(addUserState) {
						name.isNotBlank() && paternal.isNotBlank() && maternal.isNotBlank()
								&& newPassword.isNotBlank() && (
								when (addUserState.userTypeUI) {
									UserTypeUI.Patient -> personKeyPatient?.isNotBlank() == true
									UserTypeUI.Doctor -> doctorLicense != null && doctorSpeciality != null
									UserTypeUI.Receptionist -> true
								}
								)
					}
				) {
					Text(text = stringResource(id = R.string.add_user))
				}
			}
		}
	}
}

@Composable
fun NewUserDetails(response: CreatedUserResponse) {
	OutlinedCard(
		modifier = Modifier.fillMaxWidth(0.8f),
		colors = CardDefaults.outlinedCardColors(
			containerColor = MaterialTheme.colorScheme.secondaryContainer,
			contentColor = MaterialTheme.colorScheme.onSecondaryContainer
		)
	) {
		Text(
			text = stringResource(id = R.string.username_new_user, response.username)
		)
	}
}
