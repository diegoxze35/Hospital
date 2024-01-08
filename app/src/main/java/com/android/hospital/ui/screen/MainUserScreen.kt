package com.android.hospital.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.unit.dp
import com.android.hospital.R
import com.android.hospital.ui.components.MainUserScreenButton

@Composable
fun MainUserScreen(
	modifier: Modifier = Modifier,
	userOptions: List<String>,
	icons: List<ImageVector>,
	onClickOptions: List<() -> Unit>,
) {
	LazyVerticalGrid(
		modifier = modifier,
		columns = GridCells.Fixed(
			count = integerResource(id = R.integer.grid_count)
		),
		horizontalArrangement = Arrangement.spacedBy(18.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
		contentPadding = PaddingValues(all = 8.dp)
	) {
		itemsIndexed(userOptions) { index, item ->
			MainUserScreenButton(
				buttonText = item,
				icon = icons[index],
				onClick = onClickOptions[index]
			)
		}
	}
}