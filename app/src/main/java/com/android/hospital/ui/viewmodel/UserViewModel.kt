package com.android.hospital.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.hospital.data.api.HospitalUserRepository
import com.android.hospital.data.domain.AddUserRequest
import com.android.hospital.data.domain.DoctorWithSpecialityResponse
import com.android.hospital.data.domain.EditMedicalCiteRequest
import com.android.hospital.data.domain.MedicCiteRequest
import com.android.hospital.data.domain.MedicalPrescription
import com.android.hospital.data.domain.MedicalSpeciality
import com.android.hospital.data.domain.UpdateUserStateResponse
import com.android.hospital.data.domain.UserResponse
import com.android.hospital.data.domain.medicalappointment.MedicalAppointment
import com.android.hospital.data.domain.medicalappointment.MedicalAppointmentOfUser
import com.android.hospital.data.domain.user.DoctorUser
import com.android.hospital.data.domain.user.PatientUser
import com.android.hospital.data.domain.user.User
import com.android.hospital.toDomain
import com.android.hospital.ui.domain.UpdateIsActiveUserRequest
import com.android.hospital.ui.viewmodel.state.AddUserUIState
import com.android.hospital.ui.viewmodel.state.ScheduleMedicCiteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
	private val repository: HospitalUserRepository,
) : ViewModel() {
	var currentUser: User? by mutableStateOf(null)
	val allMedicalSpecialities = mutableStateListOf<MedicalSpeciality>()
	
	fun getAllMedicalSpecialities() {
		if (allMedicalSpecialities.isNotEmpty()) return
		viewModelScope.launch {
			allMedicalSpecialities.addAll(repository.getAllSpecialitiesNames())
		}
	}
	
	private val _addUserUIState: MutableStateFlow<AddUserUIState> =
		MutableStateFlow(AddUserUIState.AddingUserUI)
	val specialitiesState = _addUserUIState.asStateFlow()
	
	fun addNewUser() {
		_addUserUIState.value = AddUserUIState.AddingUserUI
	}
	
	fun addUser(addUserRequest: AddUserRequest) {
		_addUserUIState.value = AddUserUIState.LoadingAddedUserUI
		viewModelScope.launch {
			repository.addUser(addUserRequest).also {
				_addUserUIState.value = AddUserUIState.UserCreatedUI(it)
			}
		}
	}
	
	val doctorsBySpeciality = mutableStateListOf<DoctorWithSpecialityResponse>()
	fun getAllDoctorsBySpeciality(specialityName: String) {
		doctorsBySpeciality.clear()
		viewModelScope.launch {
			doctorsBySpeciality.addAll(repository.getAllDoctorsFromSpeciality(specialityName))
		}
	}
	
	
	fun getAvailableTimesForMedicalCites(
		doctorId: Int,
		roomNumber: Int,
		date: String,
		onSuccess: (List<String>) -> Unit,
	) {
		viewModelScope.launch {
			repository.getAvailableHours(date, doctorId, roomNumber).also {
				onSuccess(it)
			}
		}
	}
	
	val allRoomsNumbers = mutableStateListOf<Int>()
	fun getAllRoomNumbers() {
		if (allRoomsNumbers.isNotEmpty()) return
		viewModelScope.launch {
			repository.getAllRoomNumbers().also {
				allRoomsNumbers.addAll(it)
			}
		}
	}
	
	private val _scheduleMedicCiteState =
		MutableStateFlow<ScheduleMedicCiteState>(ScheduleMedicCiteState.Loading)
	val scheduleMedicCiteState = _scheduleMedicCiteState.asStateFlow()
	
	fun registerMedicCite(medicCiteRequest: MedicCiteRequest) {
		_scheduleMedicCiteState.value = ScheduleMedicCiteState.Loading
		viewModelScope.launch {
			_scheduleMedicCiteState.value =
				ScheduleMedicCiteState.Success(repository.scheduleMedicCiteState(medicCiteRequest))
		}
	}
	
	private val _currentMedicalAppointments = MutableStateFlow(emptyList<MedicalAppointment>())
	val currentMedicalAppointments = _currentMedicalAppointments.asStateFlow()
	fun getAllMedicalAppointmentsOfUser(statusId: Int) {
		_currentMedicalAppointments.value = emptyList()
		viewModelScope.launch {
			val ofUser = when (currentUser) {
				is PatientUser -> MedicalAppointmentOfUser.Patient
				is DoctorUser -> MedicalAppointmentOfUser.Doctor
				else -> return@launch
			}
			_currentMedicalAppointments.value =
				repository.getAllMedicalAppointmentsOfUser(statusId, ofUser).map {
					it.toDomain()
				}
		}
	}
	
	fun updateRegisterCite(editMedicalCiteRequest: EditMedicalCiteRequest) {
		viewModelScope.launch {
			repository.updateRegisterCite(editMedicalCiteRequest)
		}
	}
	
	fun cancelCite(citeId: Int) {
		viewModelScope.launch {
			repository.cancelCite(citeId)
			getAllMedicalAppointmentsOfUser(statusId = 2)
		}
	}
	
	private val _currentPrescriptions =
		MutableStateFlow<List<MedicalPrescription>>(emptyList())
	val currentPrescription = _currentPrescriptions.asStateFlow()
	fun getPrescriptionsOfPatient(patientName: String) {
		viewModelScope.launch {
			_currentPrescriptions.value = repository.getPrescriptionsOfPatient(patientName)
		}
	}
	
	private val _allUsers = MutableStateFlow<List<UserResponse>>(emptyList())
	val allUsers = _allUsers.asStateFlow()
	fun getAllPatientsAndDoctors() {
		viewModelScope.launch {
			_allUsers.value = repository.getAllPatientAndDoctors()
		}
	}
	var currentFail: UpdateUserStateResponse.CannotUpdateUserState? by mutableStateOf(null)
	fun updateActiveUser(request: UpdateIsActiveUserRequest) {
		viewModelScope.launch {
			val result = repository.updateUserActive(request)
			when (result) {
				is UpdateUserStateResponse.CannotUpdateUserState -> currentFail = result
				UpdateUserStateResponse.Success -> getAllPatientsAndDoctors()
			}
		}
	}
}
