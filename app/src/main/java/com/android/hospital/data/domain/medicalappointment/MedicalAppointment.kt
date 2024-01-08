package com.android.hospital.data.domain.medicalappointment

import java.util.Date


sealed class MedicalAppointment {
	abstract val date: Date
	abstract val roomNumber: Int
}