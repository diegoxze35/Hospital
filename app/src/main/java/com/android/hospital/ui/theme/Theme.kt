package com.android.hospital.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.android.hospital.data.domain.user.DoctorUser
import com.android.hospital.data.domain.user.PatientUser
import com.android.hospital.data.domain.user.ReceptionistUser
import com.android.hospital.data.domain.user.User

private val DarkColorScheme = darkColorScheme(
	primary = PrimaryDark,
	secondary = PurpleGrey80,
	tertiary = Pink80,
	background = BackgroundDark
)

private val LightColorScheme = lightColorScheme(
	primary = PrimaryLight,
	secondary = PurpleGrey40,
	tertiary = Pink40,
	background = BackgroundLight
)

private val PatientLightColorScheme = lightColorScheme(
	primary = patient_light_primary,
	onPrimary = patient_light_onPrimary,
	primaryContainer = patient_light_primaryContainer,
	onPrimaryContainer = patient_light_onPrimaryContainer,
	secondary = patient_light_secondary,
	onSecondary = patient_light_onSecondary,
	secondaryContainer = patient_light_secondaryContainer,
	onSecondaryContainer = patient_light_onSecondaryContainer,
	tertiary = patient_light_tertiary,
	onTertiary = patient_light_onTertiary,
	tertiaryContainer = patient_light_tertiaryContainer,
	onTertiaryContainer = patient_light_onTertiaryContainer,
	error = patient_light_error,
	errorContainer = patient_light_errorContainer,
	onError = patient_light_onError,
	onErrorContainer = patient_light_onErrorContainer,
	background = patient_light_background,
	onBackground = patient_light_onBackground,
	surface = patient_light_surface,
	onSurface = patient_light_onSurface,
	surfaceVariant = patient_light_surfaceVariant,
	onSurfaceVariant = patient_light_onSurfaceVariant,
	outline = patient_light_outline,
	inverseOnSurface = patient_light_inverseOnSurface,
	inverseSurface = patient_light_inverseSurface,
	inversePrimary = patient_light_inversePrimary,
	surfaceTint = patient_light_surfaceTint,
	outlineVariant = patient_light_outlineVariant,
	scrim = patient_light_scrim
)

private val PatientDarkColorScheme = darkColorScheme(
	primary = patient_dark_primary,
	onPrimary = patient_dark_onPrimary,
	primaryContainer = patient_dark_primaryContainer,
	onPrimaryContainer = patient_dark_onPrimaryContainer,
	secondary = patient_dark_secondary,
	onSecondary = patient_dark_onSecondary,
	secondaryContainer = patient_dark_secondaryContainer,
	onSecondaryContainer = patient_dark_onSecondaryContainer,
	tertiary = patient_dark_tertiary,
	onTertiary = patient_dark_onTertiary,
	tertiaryContainer = patient_dark_tertiaryContainer,
	onTertiaryContainer = patient_dark_onTertiaryContainer,
	error = patient_dark_error,
	errorContainer = patient_dark_errorContainer,
	onError = patient_dark_onError,
	onErrorContainer = patient_dark_onErrorContainer,
	background = patient_dark_background,
	onBackground = patient_dark_onBackground,
	surface = patient_dark_surface,
	onSurface = patient_dark_onSurface,
	surfaceVariant = patient_dark_surfaceVariant,
	onSurfaceVariant = patient_dark_onSurfaceVariant,
	outline = patient_dark_outline,
	inverseOnSurface = patient_dark_inverseOnSurface,
	inverseSurface = patient_dark_inverseSurface,
	inversePrimary = patient_dark_inversePrimary,
	surfaceTint = patient_dark_surfaceTint,
	outlineVariant = patient_dark_outlineVariant,
	scrim = patient_dark_scrim
)

private val DoctorLightColorScheme = lightColorScheme(
	primary = doctor_light_primary,
	onPrimary = doctor_light_onPrimary,
	primaryContainer = doctor_light_primaryContainer,
	onPrimaryContainer = doctor_light_onPrimaryContainer,
	secondary = doctor_light_secondary,
	onSecondary = doctor_light_onSecondary,
	secondaryContainer = doctor_light_secondaryContainer,
	onSecondaryContainer = doctor_light_onSecondaryContainer,
	tertiary = doctor_light_tertiary,
	onTertiary = doctor_light_onTertiary,
	tertiaryContainer = doctor_light_tertiaryContainer,
	onTertiaryContainer = doctor_light_onTertiaryContainer,
	error = doctor_light_error,
	errorContainer = doctor_light_errorContainer,
	onError = doctor_light_onError,
	onErrorContainer = doctor_light_onErrorContainer,
	background = doctor_light_background,
	onBackground = doctor_light_onBackground,
	surface = doctor_light_surface,
	onSurface = doctor_light_onSurface,
	surfaceVariant = doctor_light_surfaceVariant,
	onSurfaceVariant = doctor_light_onSurfaceVariant,
	outline = doctor_light_outline,
	inverseOnSurface = doctor_light_inverseOnSurface,
	inverseSurface = doctor_light_inverseSurface,
	inversePrimary = doctor_light_inversePrimary,
	surfaceTint = doctor_light_surfaceTint,
	outlineVariant = doctor_light_outlineVariant,
	scrim = doctor_light_scrim
)

private val DoctorDarkColorScheme = darkColorScheme(
	primary = doctor_dark_primary,
	onPrimary = doctor_dark_onPrimary,
	primaryContainer = doctor_dark_primaryContainer,
	onPrimaryContainer = doctor_dark_onPrimaryContainer,
	secondary = doctor_dark_secondary,
	onSecondary = doctor_dark_onSecondary,
	secondaryContainer = doctor_dark_secondaryContainer,
	onSecondaryContainer = doctor_dark_onSecondaryContainer,
	tertiary = doctor_dark_tertiary,
	onTertiary = doctor_dark_onTertiary,
	tertiaryContainer = doctor_dark_tertiaryContainer,
	onTertiaryContainer = doctor_dark_onTertiaryContainer,
	error = doctor_dark_error,
	errorContainer = doctor_dark_errorContainer,
	onError = doctor_dark_onError,
	onErrorContainer = doctor_dark_onErrorContainer,
	background = doctor_dark_background,
	onBackground = doctor_dark_onBackground,
	surface = doctor_dark_surface,
	onSurface = doctor_dark_onSurface,
	surfaceVariant = doctor_dark_surfaceVariant,
	onSurfaceVariant = doctor_dark_onSurfaceVariant,
	outline = doctor_dark_outline,
	inverseOnSurface = doctor_dark_inverseOnSurface,
	inverseSurface = doctor_dark_inverseSurface,
	inversePrimary = doctor_dark_inversePrimary,
	surfaceTint = doctor_dark_surfaceTint,
	outlineVariant = doctor_dark_outlineVariant,
	scrim = doctor_dark_scrim
)

private val ReceptionistLightColorScheme = lightColorScheme(
	primary = receptionist_light_primary,
	onPrimary = receptionist_light_onPrimary,
	primaryContainer = receptionist_light_primaryContainer,
	onPrimaryContainer = receptionist_light_onPrimaryContainer,
	secondary = receptionist_light_secondary,
	onSecondary = receptionist_light_onSecondary,
	secondaryContainer = receptionist_light_secondaryContainer,
	onSecondaryContainer = receptionist_light_onSecondaryContainer,
	tertiary = receptionist_light_tertiary,
	onTertiary = receptionist_light_onTertiary,
	tertiaryContainer = receptionist_light_tertiaryContainer,
	onTertiaryContainer = receptionist_light_onTertiaryContainer,
	error = receptionist_light_error,
	errorContainer = receptionist_light_errorContainer,
	onError = receptionist_light_onError,
	onErrorContainer = receptionist_light_onErrorContainer,
	background = receptionist_light_background,
	onBackground = receptionist_light_onBackground,
	surface = receptionist_light_surface,
	onSurface = receptionist_light_onSurface,
	surfaceVariant = receptionist_light_surfaceVariant,
	onSurfaceVariant = receptionist_light_onSurfaceVariant,
	outline = receptionist_light_outline,
	inverseOnSurface = receptionist_light_inverseOnSurface,
	inverseSurface = receptionist_light_inverseSurface,
	inversePrimary = receptionist_light_inversePrimary,
	surfaceTint = receptionist_light_surfaceTint,
	outlineVariant = receptionist_light_outlineVariant,
	scrim = receptionist_light_scrim
)

private val ReceptionistDarkColorScheme = darkColorScheme(
	primary = receptionist_dark_primary,
	onPrimary = receptionist_dark_onPrimary,
	primaryContainer = receptionist_dark_primaryContainer,
	onPrimaryContainer = receptionist_dark_onPrimaryContainer,
	secondary = receptionist_dark_secondary,
	onSecondary = receptionist_dark_onSecondary,
	secondaryContainer = receptionist_dark_secondaryContainer,
	onSecondaryContainer = receptionist_dark_onSecondaryContainer,
	tertiary = receptionist_dark_tertiary,
	onTertiary = receptionist_dark_onTertiary,
	tertiaryContainer = receptionist_dark_tertiaryContainer,
	onTertiaryContainer = receptionist_dark_onTertiaryContainer,
	error = receptionist_dark_error,
	errorContainer = receptionist_dark_errorContainer,
	onError = receptionist_dark_onError,
	onErrorContainer = receptionist_dark_onErrorContainer,
	background = receptionist_dark_background,
	onBackground = receptionist_dark_onBackground,
	surface = receptionist_dark_surface,
	onSurface = receptionist_dark_onSurface,
	surfaceVariant = receptionist_dark_surfaceVariant,
	onSurfaceVariant = receptionist_dark_onSurfaceVariant,
	outline = receptionist_dark_outline,
	inverseOnSurface = receptionist_dark_inverseOnSurface,
	inverseSurface = receptionist_dark_inverseSurface,
	inversePrimary = receptionist_dark_inversePrimary,
	surfaceTint = receptionist_dark_surfaceTint,
	outlineVariant = receptionist_dark_outlineVariant,
	scrim = receptionist_dark_scrim
)

@Composable
fun HospitalTheme(
	userTheme: User?,
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit,
) {
	val colorScheme = when (userTheme) {
		is DoctorUser -> if (darkTheme) DoctorDarkColorScheme else DoctorLightColorScheme
		is PatientUser -> if (darkTheme) PatientDarkColorScheme else PatientLightColorScheme
		is ReceptionistUser -> if (darkTheme) ReceptionistDarkColorScheme else ReceptionistLightColorScheme
		null -> if (darkTheme) DarkColorScheme else LightColorScheme
	}
	val view = LocalView.current
	if (!view.isInEditMode) {
		SideEffect {
			val window = (view.context as Activity).window
			window.statusBarColor = colorScheme.primary.toArgb()
			WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
		}
	}
	
	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	)
}