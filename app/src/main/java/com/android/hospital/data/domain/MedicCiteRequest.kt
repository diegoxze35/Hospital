package com.android.hospital.data.domain

import kotlinx.serialization.Serializable

@Serializable
data class MedicCiteRequest(
	val doctorId: Int,
	val date: String,
	val roomNumber: Int
)
