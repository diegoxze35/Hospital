package com.android.hospital.ui.screen.login

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.hospital.data.domain.UserCredentials

@Composable
fun LandscapeLoginContent(
	modifier: Modifier = Modifier,
	imageLogin: @Composable (Modifier) -> Unit,
	credentials: UserCredentials,
	onUsernameChange: (String) -> Unit,
	onPasswordChange: (String) -> Unit,
	canTryLogin: Boolean,
	loading: Boolean,
	onClickLogin: () -> Unit,
) = Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
	imageLogin(Modifier.weight(0.3f))
	LoginContent(
		modifier = Modifier.padding(horizontal = 16.dp).weight(1f),
		credentials = credentials,
		onUsernameChange = onUsernameChange,
		onPasswordChange = onPasswordChange,
		canTryLogin = canTryLogin,
		loading = loading,
		onClickLogin = onClickLogin,
	)
}