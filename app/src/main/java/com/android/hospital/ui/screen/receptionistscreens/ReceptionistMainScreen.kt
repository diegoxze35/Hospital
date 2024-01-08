package com.android.hospital.ui.screen.receptionistscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import com.android.hospital.R

@Composable
fun ReceptionistMainScreen(
	modifier: Modifier = Modifier,
) {
	LazyVerticalGrid(
		modifier = modifier,
		columns = GridCells.Fixed(
			count = integerResource(id = R.integer.grid_count)
		),
		horizontalArrangement = Arrangement.SpaceEvenly
	) {
		item {
			Text(text = "screen content in development")
		}
	}
}