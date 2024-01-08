package com.android.hospital.data.domain

import kotlinx.serialization.Serializable

@Serializable
data class MedicalSpeciality(
	val name: String,
	val citeAmount: Double
)

