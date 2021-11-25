package com.careindia.lifeskills.viewmodelfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModel


class HouseholdProfileViewModelFactory(private val hhrepository: HouseholdProfileRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HouseholdProfileViewModel::class.java)){
            return HouseholdProfileViewModel(hhrepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }


}