package com.android.hospital.data.repository

import com.android.hospital.data.api.HospitalUserRepository
import com.android.hospital.data.api.MedicalAppointmentResponse
import com.android.hospital.data.domain.AddUserRequest
import com.android.hospital.data.domain.CreatedUserResponse
import com.android.hospital.data.domain.DoctorWithSpecialityResponse
import com.android.hospital.data.domain.EditMedicalCiteRequest
import com.android.hospital.data.domain.EmitTicketRequest
import com.android.hospital.data.domain.MedicCiteRequest
import com.android.hospital.data.domain.MedicalPrescription
import com.android.hospital.data.domain.MedicalSpeciality
import com.android.hospital.data.domain.ScheduleCiteResponse
import com.android.hospital.data.domain.TicketResponse
import com.android.hospital.data.domain.UpdateUserStateResponse
import com.android.hospital.data.domain.UserResponse
import com.android.hospital.data.domain.medicalappointment.MedicalAppointmentOfUser
import com.android.hospital.ui.domain.UpdateIsActiveUserRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Named

class HospitalUserRepositoryImpl @Inject constructor(
	@Named("DefaultClient") private val client: HttpClient,
) : HospitalUserRepository {
	override suspend fun getAllSpecialitiesNames(): List<MedicalSpeciality> =
		withContext(Dispatchers.IO) {
			val response = client.get("allSpecialities")
			return@withContext response.body<List<MedicalSpeciality>>()
		}
	
	override suspend fun addUser(request: AddUserRequest): CreatedUserResponse =
		withContext(Dispatchers.IO) {
			val finalRequest = Json.encodeToString(request)
			val response = client.post("addUser") {
				contentType(ContentType.Application.Json)
				setBody(finalRequest)
			}
			return@withContext response.body<CreatedUserResponse>()
		}
	
	override suspend fun getAllDoctorsFromSpeciality(speciality: String): List<DoctorWithSpecialityResponse> {
		return withContext(Dispatchers.IO) {
			val response = client.get("allDoctorsBySpecialities") {
				parameter(key = "specialityName", value = speciality)
			}
			response.body()
		}
	}
	
	override suspend fun getAllRoomNumbers(): List<Int> = withContext(Dispatchers.IO) {
		val response = client.get("allRoomNumbers")
		return@withContext response.body<List<Int>>()
	}
	
	override suspend fun getAvailableHours(
		date: String,
		doctorId: Int,
		roomNumber: Int,
	): List<String> {
		return withContext(Dispatchers.IO) {
			client.get("medicalAppointmentTimes") {
				contentType(ContentType.Application.Json)
				parameter(key = "date", value = date)
				parameter(key = "doctorId", value = "$doctorId")
				parameter(key = "roomNumber", value = "$roomNumber")
			}.body()
		}
	}
	
	override suspend fun scheduleMedicCiteState(medicCiteRequest: MedicCiteRequest): ScheduleCiteResponse {
		return withContext(Dispatchers.IO) {
			val response = client.post("scheduleMedicalAppointment") {
				contentType(ContentType.Application.Json)
				setBody(medicCiteRequest)
			}
			response.body()
		}
	}
	
	override suspend fun getAllMedicalAppointmentsOfUser(
		statusId: Int,
		ofUser: MedicalAppointmentOfUser,
	): List<MedicalAppointmentResponse> {
		return withContext(Dispatchers.IO) {
			val endPoint = when (ofUser) {
				MedicalAppointmentOfUser.Patient -> "medicalAppointments"
				MedicalAppointmentOfUser.Doctor -> "doctorMedicalAppointments"
			}
			val response = client.get(endPoint) {
				parameter(key = "statusId", statusId)
			}
			response.body()
		}
	}
	
	override suspend fun updateRegisterCite(editMedicalCiteRequest: EditMedicalCiteRequest) {
		withContext(Dispatchers.IO) {
			val response = client.put("updateMedicalAppointment") {
				contentType(ContentType.Application.Json)
				setBody(editMedicalCiteRequest)
			}
			println(response.status.value)
		}
	}
	
	override suspend fun cancelCite(citeId: Int) {
		@Serializable
		data class CancelCiteRequest(
			val citeId: Int,
		)
		withContext(Dispatchers.IO) {
			val response = client.put("cancelCite") {
				contentType(ContentType.Application.Json)
				setBody(CancelCiteRequest(citeId = citeId))
			}
			println(response.status.value)
		}
	}
	
	override suspend fun getPrescriptionsOfPatient(patientName: String): List<MedicalPrescription> {
		return withContext(Dispatchers.IO) {
			val response = client.get("generateMedicalPrescription") {
				parameter(key = "patientFullName", value = patientName)
			}
			response.body()
		}
	}
	
	override suspend fun getAllPatientAndDoctors(): List<UserResponse> {
		return withContext(Dispatchers.IO) {
			val response = client.get("allUsers")
			response.body()
		}
	}
	
	override suspend fun updateUserActive(request: UpdateIsActiveUserRequest): UpdateUserStateResponse {
		return withContext(Dispatchers.IO) {
			val response = client.put("updateIsActive") {
				contentType(ContentType.Application.Json)
				setBody(request)
			}
			return@withContext if (response.status == HttpStatusCode.NotAcceptable)
				UpdateUserStateResponse.CannotUpdateUserState(response.body())
			else UpdateUserStateResponse.Success
		}
	}
	
	override suspend fun getAllTickets(): List<TicketResponse> {
		return withContext(Dispatchers.IO) {
			client.get("allTickets").body()
		}
	}
	
	override suspend fun emitTickets(emitTicketRequest: EmitTicketRequest) {
		return withContext(Dispatchers.IO) {
			val response = client.delete("emitTicket")
			println(response.status.value)
		}
	}
}