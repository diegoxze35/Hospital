package com.android.hospital.ui.screen.patientscreens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.hospital.R
import com.android.hospital.component1
import com.android.hospital.component2
import com.android.hospital.component3
import com.android.hospital.ui.components.SelectionDropDownMenu
import java.util.Locale.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.MedicalAppointmentsTimeScreen(
	consultingRooms: List<Int>,
	withDoctor: String,
	onDateSelect: (Int, String) -> Unit,
	availableHours: List<String>,
	onConfirmCite: (Int, String) -> Unit,
) {
	var roomNumber by rememberSaveable { mutableIntStateOf(consultingRooms.first()) }
	var selectedDate by rememberSaveable { mutableStateOf(String()) }
	var finalDate: String by rememberSaveable {
		mutableStateOf(String())
	}
	var showConfirmDialog by rememberSaveable { mutableStateOf(false) }
	SelectionDropDownMenu(
		modifier = Modifier.align(Alignment.CenterHorizontally),
		items = consultingRooms,
		textMenu = stringResource(id = R.string.room_number, roomNumber),
		textItem = {
			Text(text = "$it")
		},
		leadingIcon = {
			Icon(
				imageVector = Icons.Outlined.MeetingRoom,
				contentDescription = stringResource(id = R.string.room_number, it)
			)
		},
		onClickItem = {
			roomNumber = it.also { newNumber ->
				if (selectedDate.isNotEmpty()) {
					onDateSelect(newNumber, selectedDate)
				}
			}
		}
	)
	val context = LocalContext.current
	val calendar = Calendar.getInstance()
	val (year, month, dayOfMonth) = calendar
	val dialog = DatePickerDialog(
		context,
		{ _, selectedYear, selectedMonth, selectedDayOfMonth ->
			selectedDate = ("$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth").also {
				onDateSelect(roomNumber, it)
			}
		},
		year,
		month,
		dayOfMonth
	)
	dialog.datePicker.minDate = with(calendar) { add(Calendar.DAY_OF_MONTH, 1); timeInMillis }
	Button(onClick = { dialog.show() }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
		Text(text = stringResource(R.string.select_a_date))
	}
	Spacer(modifier = Modifier.height(IntrinsicSize.Max))
	Text(text = selectedDate, modifier = Modifier.align(Alignment.CenterHorizontally))
	Spacer(modifier = Modifier.height(IntrinsicSize.Max))
	if (availableHours.isEmpty() && selectedDate.isNotBlank())
		CircularProgressIndicator(
			modifier = Modifier.align(Alignment.CenterHorizontally)
		)
	if (showConfirmDialog) {
		AlertDialog(
			title = {
				Text(text = stringResource(R.string.confirm_medical_cite))
			},
			text = {
				Text(
					text = stringResource(
						R.string.confirmation_message,
						withDoctor,
						finalDate,
						roomNumber
					)
				)
			},
			onDismissRequest = { showConfirmDialog = false },
			confirmButton = {
				Button(onClick = { onConfirmCite(roomNumber, finalDate) }) {
					Text(text = stringResource(R.string.confirm))
				}
			},
			dismissButton = {
				Button(onClick = { showConfirmDialog = false }) {
					Text(text = stringResource(R.string.cancel))
				}
			}
		)
	}
	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		items(availableHours) {
			Card(
				colors = CardDefaults.cardColors(
					containerColor = MaterialTheme.colorScheme.secondaryContainer,
					contentColor = MaterialTheme.colorScheme.onSecondaryContainer
				),
				onClick = {
					finalDate = "$selectedDate $it"
					showConfirmDialog = true
				}
			) {
				Text(
					text = it,
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 8.dp),
					style = MaterialTheme.typography.titleMedium,
					textAlign = TextAlign.Center
				)
			}
		}
	}
}