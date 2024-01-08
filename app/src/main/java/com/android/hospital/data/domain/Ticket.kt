package com.android.hospital.data.domain
import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
	val ticketId: Int,
	val concept: String,
	val amount: Double
)