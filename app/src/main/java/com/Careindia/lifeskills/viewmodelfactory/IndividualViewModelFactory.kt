package com.careindia.lifeskills.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel

class IndividualViewModelFactory(private val imProfileRepository: IndividualProfileRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IndividualProfileViewModel::class.java)) {
            return IndividualProfileViewModel(imProfileRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}