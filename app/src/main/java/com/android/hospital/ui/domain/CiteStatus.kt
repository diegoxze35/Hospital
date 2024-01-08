package com.android.hospital.ui.domain

import androidx.annotation.StringRes
import com.android.hospital.R

enum class CiteStatus(@StringRes val text: Int) {
	Cancelled(R.string.cite_cancelled),
	Next(R.string.next),
	Completed(R.string.completed)
}