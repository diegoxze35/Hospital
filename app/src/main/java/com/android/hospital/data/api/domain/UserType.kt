package com.android.hospital.data.api.domain

import kotlinx.serialization.Serializable

@Serializable
enum class UserType {
	Patient, Doctor, Receptionist
}