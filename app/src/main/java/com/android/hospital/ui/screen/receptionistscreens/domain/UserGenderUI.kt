package com.android.hospital.ui.screen.receptionistscreens.domain

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.hospital.R

enum class UserGenderUI(
	@StringRes val textId: Int,
	val icon: ImageVector,
) {
	Male(R.string.male, Icons.Default.Male),
	Female(R.string.female, Icons.Default.Female)
}