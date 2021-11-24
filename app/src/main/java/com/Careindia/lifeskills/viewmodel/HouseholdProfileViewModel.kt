package com.careindia.lifeskills.viewmodel


import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HouseholdProfileViewModel(private val hhrepository: HouseholdProfileRepository): ViewModel() {

    val hhProfileData = hhrepository.hhProfileData


    val Date = MutableLiveData<String?>()

    val saveandnextText = MutableLiveData<String>()

    init {
        saveandnextText.value = "Save & Next"
    }

    fun saveandUpdateHHProfile(){
        val date:String = Date.value!!
        insert(HouseholdProfileEntity(0,"","","","",0,"",0,date,"","",0,0,0,0,0,0,0,0,0,0,0,0,0,"",0,0,0,"",0,"",0,0))
        Date.value = null
    }

    fun insert(hhProfileEntity: HouseholdProfileEntity){
        viewModelScope.launch(Dispatchers.IO) {
            hhrepository.insert(hhProfileEntity)
        }
    }



}