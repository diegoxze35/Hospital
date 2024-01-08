package com.android.hospital.ui.screen

sealed class Screen(val route: String) {
	
	object LoginScreen : Screen(LoginScreen::class.java.simpleName)
	
	object UserMainScreen : Screen(UserMainScreen::class.java.simpleName)
	
	object ConfirmationMedicCiteScreen : Screen(ConfirmationMedicCiteScreen::class.java.simpleName)
	
	object MedicalCitesScreen : Screen(MedicalCitesScreen::class.java.simpleName)
	
	object AddUserScreen : Screen(AddUserScreen::class.java.simpleName)
	
	object AllMedicalSpecialitiesScreen : Screen(AllMedicalSpecialitiesScreen::class.java.simpleName)
	
	object HourOfMedicalCitesScreen : Screen(HourOfMedicalCitesScreen::class.java.simpleName)
	
	object EditMedicalCiteSuccessScreen : Screen(EditMedicalCiteSuccessScreen::class.java.simpleName)
	
	object PrescriptionScreen : Screen(PrescriptionScreen::class.java.simpleName)
	
	object AllUsersScreen : Screen(AllUsersScreen::class.java.simpleName)
}
