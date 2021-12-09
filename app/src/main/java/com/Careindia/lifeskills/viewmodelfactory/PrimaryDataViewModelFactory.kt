package com.careindia.lifeskills.viewmodelfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel


class PrimaryDataViewModelFactory(private val primaryDataRepository: PrimaryDataRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrimaryDataViewModel::class.java)){
            return PrimaryDataViewModel(primaryDataRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }


}