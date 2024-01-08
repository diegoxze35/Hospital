package com.android.hospital.data.usecase

interface UseCase<in IN, out OUT> {
	suspend operator fun invoke(input: IN): OUT
}