package com.android.hospital.ui.screen.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.hospital.R
import com.android.hospital.data.domain.UserGender
import com.android.hospital.data.domain.user.DoctorUser
import com.android.hospital.data.domain.user.PatientUser
import com.android.hospital.data.domain.user.ReceptionistUser
import com.android.hospital.data.domain.user.User
import com.android.hospital.ui.theme.HospitalTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMainUserScreen(user: User, onLogout: suspend () -> Unit) {
	val coroutine = rememberCoroutineScope()
	TopAppBar(
		title = {
			Column(modifier = Modifier.padding(all = 6.dp)) {
				Text(
					text = with(user) { "$name $paternal $maternal" },
					style = MaterialTheme.typography.titleLarge
				)
				when (user) {
					is DoctorUser -> {
						Row(
							modifier = Modifier.fillMaxWidth(0.8f),
							horizontalArrangement = Arrangement.SpaceAround
						) {
							Text(
								text = "${user.license}",
								style = MaterialTheme.typography.titleMedium
							)
							Text(
								text = user.speciality,
								style = MaterialTheme.typography.titleMedium
							)
						}
					}
					
					is PatientUser -> Text(
						text = user.personKey,
						style = MaterialTheme.typography.titleMedium
					)
					
					is ReceptionistUser -> Divider()
				}
			}
		},
		colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer,
			titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
		),
		navigationIcon = {
			OutlinedIconButton(onClick = { coroutine.launch { onLogout() } }) {
				Icon(
					imageVector = Icons.Outlined.Login,
					contentDescription = stringResource(R.string.close_session)
				)
			}
		}
	)
}

@Preview
@Composable
fun PreviewTopAppBar() {
	val user = DoctorUser(
		name = "Diego",
		paternal = "Moreno",
		maternal = "Martínez",
		gender = UserGender.Male,
		license = 2021601366,
		speciality = "Cardiología",
		isActive = true
	)
	HospitalTheme(userTheme = user) {
		TopAppBarMainUserScreen(user = user) {}
	}
}