package com.android.hospital.ui.screen.patientscreens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.hospital.R
import com.android.hospital.convert
import com.android.hospital.data.domain.DoctorWithSpecialityResponse
import com.android.hospital.data.domain.MedicalSpeciality

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalCitesScreen(
	medicalCites: List<MedicalSpeciality>,
	onSpecialitySelected: (String) -> Unit,
	loadingDoctors: Boolean,
	doctorsOfSpeciality: List<DoctorWithSpecialityResponse>,
	onClickDoctor: (DoctorWithSpecialityResponse) -> Unit,
) {
	var currentSelection by rememberSaveable {
		mutableStateOf(String())
	}
	LazyColumn(
		modifier = Modifier.fillMaxWidth()
	) {
		items(medicalCites) {
			Card(
				modifier = Modifier
					.padding(PaddingValues(vertical = 8.dp))
					.animateContentSize(),
				onClick = {
					if (currentSelection != it.name) {
						currentSelection = it.name
						onSpecialitySelected(it.name)
					}
				}
			) {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 4.dp),
					horizontalArrangement = Arrangement.SpaceEvenly,
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						imageVector = Icons.Outlined.Medication,
						contentDescription = it.name
					)
					Column(horizontalAlignment = Alignment.CenterHorizontally) {
						Text(text = it.name, style = MaterialTheme.typography.titleLarge)
						Text(text = it.citeAmount.convert(), fontWeight = FontWeight.Bold)
					}
				}
				if (currentSelection == it.name) {
					if (loadingDoctors) {
						CircularProgressIndicator()
					} else {
						Column(
							modifier = Modifier
								.align(Alignment.CenterHorizontally)
								.padding(all = 16.dp)
						) {
							Text(
								text = stringResource(R.string.select_doctor_message),
								style = MaterialTheme.typography.titleLarge,
								textAlign = TextAlign.Center
							)
							Divider()
							LazyRow(
								contentPadding = PaddingValues(all = 8.dp)
							) {
								items(doctorsOfSpeciality) { doctor ->
									Button(
										onClick = {
											onClickDoctor(doctor)
										},
										modifier = Modifier.padding(horizontal = 8.dp)
									) {
										Text(text = doctor.fullName)
									}
								}
							}
							Divider()
						}
					}
				}
			}
		}
	}
}