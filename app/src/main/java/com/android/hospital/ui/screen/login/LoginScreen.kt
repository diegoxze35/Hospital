package com.android.hospital.ui.screen.login

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.hospital.R
import com.android.hospital.data.domain.UserCredentials
import com.android.hospital.data.domain.user.User
import com.android.hospital.ui.state.AuthUserState
import com.android.hospital.ui.theme.HospitalTheme

@Composable
fun LoginScreen(
	modifier: Modifier = Modifier,
	credentials: UserCredentials,
	onUsernameChange: (String) -> Unit,
	onPasswordChange: (String) -> Unit,
	canTryLogin: Boolean,
	isLoading: Boolean,
	onClickLogin: () -> Unit,
	authState: AuthUserState,
	onLoginSuccess: (User) -> Unit,
) = Surface(
	modifier = modifier.fillMaxSize(),
	color = MaterialTheme.colorScheme.background
) {
	Box(modifier = Modifier.fillMaxSize()) {
		val imageLogin = @Composable { modifier: Modifier ->
			Image(
				painter = painterResource(id = R.drawable.image_login),
				contentDescription = null,
				modifier = modifier.then(Modifier)
			)
		}
		Image(
			painter = painterResource(id = R.drawable.decoration1),
			contentDescription = null,
			modifier = Modifier.align(Alignment.TopStart)
		)
		Crossfade(
			targetState = authState, label = "Cross fade", modifier = Modifier
				.align(Alignment.TopCenter)
				.fillMaxWidth(0.6f)
				.padding(top = 32.dp)
		) {
			when (it) {
				AuthUserState.Initial -> Divider()
				is AuthUserState.AuthenticationSuccess -> onLoginSuccess(it.user)
				is AuthUserState.AuthenticationWithIncorrectPassword ->
					Card(
						colors = CardDefaults.cardColors(
							containerColor = MaterialTheme.colorScheme.errorContainer,
							contentColor = MaterialTheme.colorScheme.onErrorContainer
						),
						elevation = CardDefaults.cardElevation(
							defaultElevation = 8.dp
						)
					) {
						Text(
							text = stringResource(
								R.string.message_incorrect_password,
								with(it.user) {
									"$type $name $paternal $maternal"
								}
							),
							modifier = Modifier.padding(all = 16.dp),
							textAlign = TextAlign.Center
						)
					}
				
				AuthUserState.UnknownUser -> Row(
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						imageVector = Icons.Default.Error,
						contentDescription = null,
						tint = MaterialTheme.colorScheme.error
					)
					Text(
						text = stringResource(R.string.user_not_found),
						color = MaterialTheme.colorScheme.error,
						textAlign = TextAlign.Center
					)
				}
			}
		}
		val centeredModifier = Modifier.align(Alignment.Center)
		if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
			LoginContent(
				modifier = centeredModifier,
				credentials = credentials,
				onUsernameChange = onUsernameChange,
				onPasswordChange = onPasswordChange,
				canTryLogin = canTryLogin,
				loading = isLoading,
				onClickLogin = onClickLogin,
			)
			imageLogin(Modifier.align(Alignment.BottomStart))
		} else {
			LandscapeLoginContent(
				modifier = centeredModifier,
				imageLogin = imageLogin,
				credentials = credentials,
				onUsernameChange = onUsernameChange,
				onPasswordChange = onPasswordChange,
				canTryLogin = canTryLogin,
				loading = isLoading,
				onClickLogin = onClickLogin,
			)
		}
		Image(
			painter = painterResource(id = R.drawable.decoration2),
			contentDescription = null,
			modifier = Modifier.align(Alignment.BottomEnd)
		)
	}
}

@Composable
@Preview(
	name = "Light", showSystemUi = true, showBackground = true, uiMode = UI_MODE_NIGHT_NO,
	device = "spec:width=720px,height=1280px,dpi=320,orientation=portrait",
)
fun PreviewLogin() {
	HospitalTheme(userTheme = null, darkTheme = false) {
		LoginScreen(
			credentials = UserCredentials(),
			onUsernameChange = {},
			onPasswordChange = {},
			canTryLogin = true,
			isLoading = false,
			onClickLogin = { },
			authState = AuthUserState.Initial
		) {}
	}
}

@Composable
@Preview(
	name = "Dark", showSystemUi = true, showBackground = true, uiMode = UI_MODE_NIGHT_YES,
	device = "spec:width=720px,height=1280px,dpi=320,orientation=portrait",
)
fun PreviewLoginDark() {
	HospitalTheme(userTheme = null, darkTheme = true) {
		LoginScreen(
			credentials = UserCredentials(),
			onUsernameChange = {},
			onPasswordChange = {},
			canTryLogin = true,
			isLoading = false,
			onClickLogin = { },
			authState = AuthUserState.Initial
		) {}
	}
}