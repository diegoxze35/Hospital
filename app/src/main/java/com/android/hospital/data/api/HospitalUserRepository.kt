package com.android.hospital.data.api

import com.android.hospital.data.domain.AddUserRequest
import com.android.hospital.data.domain.CreatedUserResponse
import com.android.hospital.data.domain.DoctorWithSpecialityResponse
import com.android.hospital.data.domain.EditMedicalCiteRequest
import com.android.hospital.data.domain.EmitTicketRequest
import com.android.hospital.data.domain.MedicCiteRequest
import com.android.hospital.data.domain.MedicalPrescription
import com.android.hospital.data.domain.medicalappointment.MedicalAppointmentOfUser
import com.android.hospital.data.domain.MedicalSpeciality
import com.android.hospital.data.domain.ScheduleCiteResponse
import com.android.hospital.data.domain.TicketResponse
import com.android.hospital.data.domain.UpdateUserStateResponse
import com.android.hospital.data.domain.UserResponse
import com.android.hospital.ui.domain.UpdateIsActiveUserRequest

interface HospitalUserRepository {
	suspend fun getAllSpecialitiesNames(): List<MedicalSpeciality>
	suspend fun addUser(request: AddUserRequest): CreatedUserResponse
	suspend fun getAllDoctorsFromSpeciality(speciality: String): List<DoctorWithSpecialityResponse>
	suspend fun getAllRoomNumbers(): List<Int>
	suspend fun getAvailableHours(date: String, doctorId: Int, roomNumber: Int): List<String>
	suspend fun scheduleMedicCiteState(medicCiteRequest: MedicCiteRequest): ScheduleCiteResponse
	suspend fun getAllMedicalAppointmentsOfUser(
		statusId: Int,
		ofUser: MedicalAppointmentOfUser,
	): List<MedicalAppointmentResponse>
	
	suspend fun updateRegisterCite(editMedicalCiteRequest: EditMedicalCiteRequest)
	suspend fun cancelCite(citeId: Int)
	suspend fun getPrescriptionsOfPatient(patientName: String): List<MedicalPrescription>
	suspend fun getAllPatientAndDoctors(): List<UserResponse>
	suspend fun updateUserActive(request: UpdateIsActiveUserRequest): UpdateUserStateResponse
	suspend fun getAllTickets(): List<TicketResponse>
	suspend fun emitTickets(emitTicketRequest: EmitTicketRequest)
}