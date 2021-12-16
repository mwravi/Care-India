package com.careindia.lifeskills.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.viewmodel.PsychometricViewModel

class PsychometricViewModelFactory (private val psychometricRepository: PsychometricRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PsychometricViewModel::class.java)) {
            return PsychometricViewModel(psychometricRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}