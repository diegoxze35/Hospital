package com.android.hospital.data.domain.user

import com.android.hospital.data.domain.UserGender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Doctor")
data class DoctorUser(
	override val name: String,
	override val paternal: String,
	override val maternal: String,
	override val gender: UserGender,
	val speciality: String,
	val license: Int,
	val isActive: Boolean
) : User()