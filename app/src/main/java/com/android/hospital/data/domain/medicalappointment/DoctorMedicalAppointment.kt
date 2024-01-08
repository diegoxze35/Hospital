package com.android.hospital.data.domain.medicalappointment

import java.util.Date

data class DoctorMedicalAppointment(
	override val date: Date,
	override val roomNumber: Int,
	val patientFullName: String,
) : MedicalAppointment()

