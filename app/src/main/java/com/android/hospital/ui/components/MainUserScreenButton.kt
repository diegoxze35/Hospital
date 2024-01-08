package com.android.hospital.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPasteSearch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.hospital.R
import com.android.hospital.ui.theme.HospitalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUserScreenButton(
	modifier: Modifier = Modifier,
	buttonText: String,
	icon: ImageVector,
	onClick: () -> Unit,
) {
	Card(
		onClick = onClick, modifier = modifier, colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.secondaryContainer,
			contentColor = MaterialTheme.colorScheme.onSecondaryContainer
		), elevation = CardDefaults.cardElevation(
			defaultElevation = 4.dp
		)
	) {
		val centerHorizontally = Modifier
			.align(Alignment.CenterHorizontally)
			.padding(all = 8.dp)
		Text(
			text = buttonText,
			style = MaterialTheme.typography.titleMedium,
			modifier = centerHorizontally,
			softWrap = true,
			textAlign = TextAlign.Center,
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
		)
		Icon(
			imageVector = icon,
			contentDescription = null,
			modifier = centerHorizontally.then(
				Modifier.requiredSize(
					dimensionResource(id = R.dimen.icon_button_size)
				)
			)
		)
	}
}

@Preview
@Composable
fun ButtonPreview() = HospitalTheme(userTheme = null) {
	MainUserScreenButton(
		buttonText = "Sample button Sample button Sample button",
		icon = Icons.Outlined.ContentPasteSearch
	) {
	
	}
}
