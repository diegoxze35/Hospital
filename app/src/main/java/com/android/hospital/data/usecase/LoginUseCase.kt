package com.android.hospital.data.usecase

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.android.hospital.TOKEN_KEY
import com.android.hospital.data.api.HospitalLoginApi
import com.android.hospital.data.domain.UserCredentials
import com.android.hospital.data.repository.domain.AuthResult
import com.android.hospital.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
	private val apiService: HospitalLoginApi,
	private val context: Context
) : UseCase<UserCredentials, AuthResult> {
	override suspend fun invoke(input: UserCredentials): AuthResult {
		return withContext(Dispatchers.IO + NonCancellable) {
			val result = apiService.login(input)
			if (result is AuthResult.Authorized) {
				context.dataStore.edit { it[TOKEN_KEY] = result.token }
			}
			result
		}
	}
}