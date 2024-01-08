package com.android.hospital.data.domain.user

import com.android.hospital.data.domain.UserGender
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
sealed class User {
	abstract val name: String
	abstract val paternal: String
	abstract val maternal: String
	abstract val gender: UserGender
}
