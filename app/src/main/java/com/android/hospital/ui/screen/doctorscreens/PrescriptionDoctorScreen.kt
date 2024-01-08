package com.android.hospital.ui.screen.doctorscreens

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.hospital.R
import com.android.hospital.data.domain.MedicalPrescription
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionScreen(
	prescriptions: List<MedicalPrescription>,
	onSendPatientName: (String) -> Unit,
) {
	var patientFullName by rememberSaveable {
		mutableStateOf(String())
	}
	
	Column(modifier = Modifier.fillMaxSize()) {
		TextField(
			value = patientFullName,
			onValueChange = { patientFullName = it },
			label = {
				Text(text = stringResource(id = R.string.patient))
			},
			modifier = Modifier.align(Alignment.CenterHorizontally)
		)
		Button(
			onClick = {
				if (patientFullName.isNotBlank()) onSendPatientName(patientFullName)
			},
			modifier = Modifier.align(Alignment.CenterHorizontally)
		) {
			Text(text = "Generate")
		}
		val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH)
		formatter.timeZone = TimeZone.getTimeZone("America/Mexico_City")
		if (prescriptions.isNotEmpty()) {
			LazyColumn(
				verticalArrangement = Arrangement.spacedBy(16.dp),
				modifier = Modifier.align(Alignment.CenterHorizontally)
			) {
				items(prescriptions) {
					Card(modifier = Modifier.fillMaxWidth(0.9f)) {
						Text(
							text = stringResource(id = R.string.folio, it.folio),
							modifier = Modifier
								.align(Alignment.CenterHorizontally)
								.padding(vertical = 16.dp),
							style = MaterialTheme.typography.titleLarge
						)
						Text(text = stringResource(id = R.string.doctor) + " ${it.doctorFullName}")
						Text(
							text = stringResource(id = R.string.date_and_hour) + " ${
								formatter.format(Date(it.date * 1_000L))
							}"
						)
						Text(text = stringResource(id = R.string.diagnostic, it.diagnostic))
						Text(
							text = stringResource(id = R.string.treatment),
							style = MaterialTheme.typography.titleLarge,
							modifier = Modifier
								.align(Alignment.CenterHorizontally)
								.padding(vertical = 16.dp)
						)
						Text(text = stringResource(id = R.string.medicament, it.medicamentName))
						Text(text = it.medicamentUnit)
						Text(text = it.duration)
						Text(text = it.period)
					}
				}
			}
		}
	}
}