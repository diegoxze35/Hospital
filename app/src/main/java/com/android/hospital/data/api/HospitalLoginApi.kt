package com.android.hospital.data.api

import com.android.hospital.data.domain.UserCredentials
import com.android.hospital.data.repository.domain.AuthResult

interface HospitalLoginApi {
	
	suspend fun login(credentials: UserCredentials): AuthResult
	
}