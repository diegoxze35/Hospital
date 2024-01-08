package com.android.hospital.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun <T> SelectionDropDownMenu(
	modifier: Modifier = Modifier,
	items: List<T>,
	textMenu: String,
	textItem: @Composable (T) -> Unit,
	leadingIcon: @Composable (T) -> Unit,
	onClickItem: (T) -> Unit,
) {
	var expanded by rememberSaveable {
		mutableStateOf(false)
	}
	Column(modifier) {
		TextButton(
			onClick = { expanded = !expanded }
		) {
			Icon(
				imageVector = if (!expanded)
					Icons.Default.ArrowDropDown
				else Icons.Default.ArrowDropUp,
				contentDescription = null
			)
			Text(text = textMenu)
		}
		DropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false }
		) {
			items.forEach {
				DropdownMenuItem(
					text = {
						textItem(it)
					},
					leadingIcon = {
						leadingIcon(it)
					},
					onClick = {
						onClickItem(it)
						expanded = false
					}
				)
			}
		}
	}
}

@Composable
fun <T> SelectionDropDownMenu(
	modifier: Modifier = Modifier,
	items: Array<T>,
	textMenu: String,
	textItem: @Composable (T) -> Unit,
	leadingIcon: @Composable (T) -> Unit,
	onClickItem: (T) -> Unit,
) = SelectionDropDownMenu(
	modifier = modifier,
	items = items.toList(),
	textMenu = textMenu,
	textItem = textItem,
	leadingIcon = leadingIcon,
	onClickItem = onClickItem
)