package com.android.hospital.ui.screen.receptionistscreens.domain

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.hospital.R

enum class UserTypeUI(@StringRes val textId: Int, val icon: ImageVector) {
	Patient(R.string.patient, Icons.Outlined.Person),
	Doctor(R.string.doctor, Icons.Outlined.LocalHospital),
	Receptionist(R.string.receptionist, Icons.Outlined.AdminPanelSettings)
}