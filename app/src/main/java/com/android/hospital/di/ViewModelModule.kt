package com.android.hospital.di

import android.content.Context
import com.android.hospital.data.api.HospitalLoginApi
import com.android.hospital.data.api.HospitalUserRepository
import com.android.hospital.data.repository.HospitalUserRepositoryImpl
import com.android.hospital.data.repository.LoginRepository
import com.android.hospital.data.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
@Suppress("unused")
object ViewModelModule {
	
	@Provides
	fun provideLoginService(@Named("LoginClient") client: HttpClient): HospitalLoginApi =
		LoginRepository(client)
	
	@Provides
	fun providesLoginUseCase(apiService: HospitalLoginApi, @ApplicationContext context: Context) =
		LoginUseCase(apiService, context)
	
	@Provides
	fun providesUserRepository(
		@Named("DefaultClient") client: HttpClient
	): HospitalUserRepository = HospitalUserRepositoryImpl(client)
}