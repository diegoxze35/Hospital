package com.android.hospital.ui.components

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.android.hospital.R
import com.android.hospital.data.domain.medicalappointment.PatientMedicalAppointment
import com.android.hospital.ui.domain.CiteStatus
import java.util.Locale

@Composable
fun PatientMedicalCiteCard(
	modifier: Modifier = Modifier,
	cite: PatientMedicalAppointment,
	status: CiteStatus,
	onModifyCite: () -> Unit,
	onCancelCite: (Int) -> Unit,
) {
	val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH)
	formatter.timeZone = TimeZone.getTimeZone("America/Mexico_City")
	Card(modifier = modifier.fillMaxWidth()) {
		Row {
			Column(modifier = Modifier.weight(1f)) {
				Text(text = stringResource(R.string.folio, cite.citeId))
				Text(text = stringResource(R.string.date_and_hour) + formatter.format(cite.date))
				Text(text = stringResource(R.string.consulting_room, cite.roomNumber))
				Text(text = "Doctor: ${cite.doctorFullName}")
				Text(text = stringResource(R.string.speciality, cite.speciality))
			}
			if (status == CiteStatus.Next) {
				Column {
					IconButton(onClick = onModifyCite) {
						Icon(
							imageVector = Icons.Default.Edit,
							contentDescription = null
						)
					}
					IconButton(onClick = { onCancelCite(cite.citeId) }) {
						Icon(
							imageVector = Icons.Default.Cancel,
							contentDescription = null
						)
					}
				}
			}
		}
	}
}