package com.careindia.lifeskills.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.viewmodel.CollectiveMeetingViewModel

class CollectiveMeetingViewModelFactory(private val collectiveMeetingRepository: CollectiveMeetingRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectiveMeetingViewModel::class.java)){
            return CollectiveMeetingViewModel(collectiveMeetingRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }


}