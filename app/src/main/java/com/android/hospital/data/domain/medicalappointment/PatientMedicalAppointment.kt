package com.android.hospital.data.domain.medicalappointment

import java.util.Date

data class PatientMedicalAppointment(
	override val date: Date,
	override val roomNumber: Int,
	val doctorFullName: String,
	val doctorId: Int,
	val citeId: Int,
	val speciality: String,
) : MedicalAppointment()
