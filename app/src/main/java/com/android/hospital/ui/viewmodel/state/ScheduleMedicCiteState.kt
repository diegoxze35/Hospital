package com.android.hospital.ui.viewmodel.state

import com.android.hospital.data.domain.ScheduleCiteResponse

sealed class ScheduleMedicCiteState {
	object Loading: ScheduleMedicCiteState()
	data class Success(val medicCite: ScheduleCiteResponse) : ScheduleMedicCiteState()
}
