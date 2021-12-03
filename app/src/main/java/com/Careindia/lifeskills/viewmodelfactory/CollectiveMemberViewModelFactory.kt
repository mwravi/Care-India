package com.careindia.lifeskills.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.repository.CollectiveMemberRepository
import com.careindia.lifeskills.viewmodel.CollectiveMemberViewModel

class CollectiveMemberViewModelFactory(private val collectiveMemberRepository: CollectiveMemberRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectiveMemberViewModel::class.java)){
            return CollectiveMemberViewModel(collectiveMemberRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }


}