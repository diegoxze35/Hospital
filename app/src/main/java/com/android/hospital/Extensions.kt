package com.android.hospital

import android.content.Context
import android.icu.text.DecimalFormat
import android.icu.util.Calendar
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.android.hospital.data.api.MedicalAppointmentResponse
import com.android.hospital.data.api.domain.UserResponse
import com.android.hospital.data.api.domain.UserType
import com.android.hospital.data.domain.AddUserRequest
import com.android.hospital.data.domain.UserGender
import com.android.hospital.data.domain.medicalappointment.DoctorMedicalAppointment
import com.android.hospital.data.domain.medicalappointment.MedicalAppointment
import com.android.hospital.data.domain.medicalappointment.MedicalAppointmentOfUser
import com.android.hospital.data.domain.medicalappointment.PatientMedicalAppointment
import com.android.hospital.data.domain.user.DoctorUser
import com.android.hospital.data.domain.user.PatientUser
import com.android.hospital.data.domain.user.ReceptionistUser
import com.android.hospital.ui.screen.receptionistscreens.domain.AddUserState
import com.android.hospital.ui.screen.receptionistscreens.domain.UserGenderUI
import com.android.hospital.ui.screen.receptionistscreens.domain.UserTypeUI
import java.util.Date

private const val PREFS_KEY = "HOSPITAL_APP_PREFS"
val TOKEN_KEY = stringPreferencesKey("token")
val Context.dataStore by preferencesDataStore(name = PREFS_KEY)

fun UserResponse.toDomain() = when (type) {
	UserType.Patient -> PatientUser(name, paternal, maternal, gender, personKey!!, isActive!!)
	UserType.Doctor -> DoctorUser(name, paternal, maternal, gender, speciality!!, license!!, isActive!!)
	UserType.Receptionist -> ReceptionistUser(name, paternal, maternal, gender)
}

fun AddUserState.toDomain() = when (userTypeUI) {
	UserTypeUI.Patient -> AddUserRequest(
		newUser = PatientUser(
			name = name,
			paternal = paternal,
			maternal = maternal,
			gender = when (userGenderUI) {
				UserGenderUI.Male -> UserGender.Male
				UserGenderUI.Female -> UserGender.Female
			},
			personKey = personKeyPatient!!,
			isActive = true
		),
		password = newPassword
	)
	
	UserTypeUI.Doctor -> AddUserRequest(
		newUser = DoctorUser(
			name = name,
			paternal = paternal,
			maternal = maternal,
			gender = when (userGenderUI) {
				UserGenderUI.Male -> UserGender.Male
				UserGenderUI.Female -> UserGender.Female
			},
			license = doctorLicense!!,
			speciality = doctorSpeciality!!,
			isActive = true
		),
		password = newPassword
	)
	
	UserTypeUI.Receptionist -> AddUserRequest(
		ReceptionistUser(
			name = name,
			paternal = paternal,
			maternal = maternal,
			gender = when (userGenderUI) {
				UserGenderUI.Male -> UserGender.Male
				UserGenderUI.Female -> UserGender.Female
			}
		),
		password = newPassword
	)
}

fun Double.convert(): String {
	val format = DecimalFormat("#,###.00")
	format.isDecimalSeparatorAlwaysShown = false
	return format.format(this).toString()
}


operator fun Calendar.component1(): Int = this[Calendar.YEAR]
operator fun Calendar.component2(): Int = this[Calendar.MONTH]
operator fun Calendar.component3(): Int = this[Calendar.DAY_OF_MONTH]

fun MedicalAppointmentResponse.toDomain(): MedicalAppointment {
	return when (this.ofUser) {
		MedicalAppointmentOfUser.Patient -> PatientMedicalAppointment(
			date = Date(date * 1_000),
			roomNumber = roomNumber,
			doctorFullName = doctorFullName!!,
			doctorId = doctorId!!,
			citeId = citeId!!,
			speciality = speciality!!
		)
		
		MedicalAppointmentOfUser.Doctor -> DoctorMedicalAppointment(
			date = Date(date * 1_000),
			roomNumber = roomNumber,
			patientFullName = patientFullName!!
		)
	}
}