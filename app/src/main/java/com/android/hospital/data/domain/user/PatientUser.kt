package com.android.hospital.data.domain.user

import com.android.hospital.data.domain.UserGender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Patient")
data class PatientUser(
	override val name: String,
	override val paternal: String,
	override val maternal: String,
	override val gender: UserGender,
	val personKey: String,
	val isActive: Boolean
) : User()