package com.android.hospital.data.domain

import kotlinx.serialization.Serializable

@Serializable
data class MedicalPrescription(
	val folio: Int,
	val date: Long,
	val patientFullName: String,
	val doctorFullName: String,
	val diagnostic: String,
	val medicamentName: String,
	val medicamentUnit: String,
	val duration: String,
	val period: String
)

