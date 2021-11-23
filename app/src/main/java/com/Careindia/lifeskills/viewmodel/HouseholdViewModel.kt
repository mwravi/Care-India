package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.repository.HouseholdProfileRepository


class HouseholdViewModel: AndroidViewModel {
    var householdRepository:HouseholdProfileRepository?= null


    constructor(application: CareIndiaApplication):super (application){
        householdRepository = HouseholdProfileRepository(application)
    }


}