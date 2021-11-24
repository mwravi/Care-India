package com.careindia.lifeskills.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import kotlinx.coroutines.launch
import java.util.*

class HouseholdProfileViewModelFactory(private val hhrepository: HouseholdProfileRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(HouseholdProfileViewModel::class.java)){
           return HouseholdProfileViewModel(hhrepository) as T
       }
        throw IllegalArgumentException("Unknown View Model Class")
    }


}