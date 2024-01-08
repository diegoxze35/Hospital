package com.android.hospital.di

import android.content.Context
import com.android.hospital.TOKEN_KEY
import com.android.hospital.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.bearerAuth
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Named
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	
	@OptIn(ExperimentalSerializationApi::class)
	@Provides
	@Singleton
	@Named("DefaultClient")
	fun provideHttpClient(@ApplicationContext context: Context): HttpClient {
		return HttpClient(Android) {
			engine {
				connectTimeout = 12_000
				socketTimeout = 12_000
				/*requestConfig = {
					useCaches = false
				}*/
			}
			defaultRequest {
				url(urlString = "http://192.168.1.10:8080/api/")
				val preferences = runBlocking { context.dataStore.data.first() }
				bearerAuth(preferences[TOKEN_KEY].orEmpty())
			}
			install(ContentNegotiation) {
				json(
					Json {
						encodeDefaults = true
						explicitNulls = true
					}
				)
			}
		}
	}
	
	
	@OptIn(ExperimentalSerializationApi::class)
	@Provides
	@Named("LoginClient")
	fun providesLoginClient(): HttpClient {
		return HttpClient(Android) {
			engine {
				connectTimeout = 15_000
				socketTimeout = 15_000
				requestConfig = {
					useCaches = true
				}
			}
			defaultRequest {
				url("http://192.168.1.10:8080/api/login")
			}
			install(ContentNegotiation) {
				json(
					Json {
						encodeDefaults = true
						explicitNulls = true
					}
				)
			}
		}
	}
	
}