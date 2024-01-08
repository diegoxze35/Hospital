package com.android.hospital.ui.screen.login

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.android.hospital.R
import com.android.hospital.data.domain.UserCredentials

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
	modifier: Modifier = Modifier,
	credentials: UserCredentials,
	onUsernameChange: (String) -> Unit,
	onPasswordChange: (String) -> Unit,
	canTryLogin: Boolean,
	loading: Boolean,
	onClickLogin: () -> Unit,
) = Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
	Text(
		text = stringResource(id = R.string.welcome_message),
		color = MaterialTheme.colorScheme.primary,
		style = MaterialTheme.typography.titleLarge,
	)
	val modifierMaxWidth = Modifier.fillMaxWidth(fraction = 0.8f)
	OutlinedTextField(
		value = credentials.username,
		onValueChange = onUsernameChange,
		enabled = !loading,
		label = {
			Text(text = stringResource(id = R.string.username))
		},
		keyboardOptions = KeyboardOptions(
			autoCorrect = false,
			keyboardType = KeyboardType.Text,
			imeAction = ImeAction.Next
		),
		modifier = modifierMaxWidth.padding(vertical = 6.dp)
	)
	var visualTransformation: VisualTransformation by remember {
		mutableStateOf(PasswordVisualTransformation())
	}
	OutlinedTextField(
		value = credentials.password,
		onValueChange = onPasswordChange,
		enabled = !loading,
		label = {
			Text(text = stringResource(id = R.string.password))
		},
		visualTransformation = visualTransformation,
		keyboardOptions = KeyboardOptions(
			autoCorrect = false,
			keyboardType = KeyboardType.Text,
			imeAction = ImeAction.Done
		),
		modifier = modifierMaxWidth.padding(vertical = 6.dp),
		trailingIcon = {
			IconButton(
				onClick = {
					visualTransformation =
						if (visualTransformation is PasswordVisualTransformation)
							VisualTransformation.None
						else
							PasswordVisualTransformation()
				}
			) {
				Icon(
					imageVector = if (visualTransformation is PasswordVisualTransformation)
						Icons.Default.Visibility
					else
						Icons.Default.VisibilityOff, contentDescription = null
				)
			}
		}
	)
	Button(
		onClick = onClickLogin,
		enabled = canTryLogin && !loading,
		modifier = modifierMaxWidth.animateContentSize()
	) {
		if (loading) {
			CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
		} else {
			Text(text = stringResource(id = R.string.login))
		}
	}
}
