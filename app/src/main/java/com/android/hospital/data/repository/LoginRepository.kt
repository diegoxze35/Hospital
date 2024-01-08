package com.android.hospital.data.repository

import com.android.hospital.data.api.HospitalLoginApi
import com.android.hospital.data.api.domain.AuthenticatedUser
import com.android.hospital.data.domain.UserCredentials
import com.android.hospital.data.repository.domain.AuthResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Named

class LoginRepository @Inject constructor(@Named("LoginClient") private val client: HttpClient) :
	HospitalLoginApi {
	
	override suspend fun login(credentials: UserCredentials): AuthResult {
		val response = client.post {
			contentType(type = ContentType.Application.Json)
			setBody(body = credentials)
		}
		val authUser = response.body<AuthenticatedUser>()
		return if (response.status == HttpStatusCode.Unauthorized) {
			authUser.user?.let { AuthResult.AuthorizedWithIncorrectPassword(it) }
				?: AuthResult.UnknownUser
		} else
			AuthResult.Authorized(authUser.user!!, authUser.token!!)
	}
}