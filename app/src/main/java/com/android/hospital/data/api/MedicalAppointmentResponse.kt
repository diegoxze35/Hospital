package com.android.hospital.data.api

import com.android.hospital.data.domain.medicalappointment.MedicalAppointmentOfUser
import kotlinx.serialization.Serializable

@Serializable
data class MedicalAppointmentResponse(
	val ofUser: MedicalAppointmentOfUser,
	val date: Long,
	val roomNumber: Int,
	val patientFullName: String? = null,
	val doctorId: Int? = null,
	val doctorFullName: String? = null,
	val citeId: Int? = null,
	val speciality: String? = null
)
