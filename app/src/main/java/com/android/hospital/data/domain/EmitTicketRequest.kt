package com.android.hospital.data.domain

import kotlinx.serialization.Serializable
@Serializable
data class EmitTicketRequest(val patientId: Int)
