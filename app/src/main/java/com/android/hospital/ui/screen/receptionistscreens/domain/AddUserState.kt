package com.android.hospital.ui.screen.receptionistscreens.domain

data class AddUserState(
	val name: String = String(),
	val paternal: String = String(),
	val maternal: String = String(),
	val newPassword: String = String(),
	val userGenderUI: UserGenderUI = UserGenderUI.Male,
	val userTypeUI: UserTypeUI = UserTypeUI.Patient,
	val personKeyPatient: String? = null,
	val doctorLicense: Int? = null,
	val doctorSpeciality: String? = null
)
