package com.android.hospital.ui.components

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.hospital.R
import com.android.hospital.data.domain.medicalappointment.DoctorMedicalAppointment
import java.util.Locale

@Composable
fun DoctorMedicalCiteCard(cite: DoctorMedicalAppointment) {
	val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH)
	formatter.timeZone = TimeZone.getTimeZone("America/Mexico_City")
	Card(
		elevation = CardDefaults.cardElevation(
			defaultElevation = 6.dp
		),
		modifier = Modifier.fillMaxWidth(fraction = 0.8f)
	) {
		Column(modifier = Modifier.padding(all = 12.dp)) {
			Text(text = stringResource(id = R.string.date_and_hour) + formatter.format(cite.date))
			Text(text = stringResource(id = R.string.patient) + cite.patientFullName)
			Text(text = stringResource(id = R.string.consulting_room, cite.roomNumber))
		}
	}
}