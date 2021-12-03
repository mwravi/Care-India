package com.careindia.lifeskills.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.viewmodel.CollectiveViewModel

class CollectiveViewModelFactory(private val collectiveRepository: CollectiveRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectiveViewModel::class.java)){
            return CollectiveViewModel(collectiveRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }


}