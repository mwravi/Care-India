package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.repository.IndividualProfileRepository

class IndividualProfileViewModel: AndroidViewModel {
    var individualProfileRepository:IndividualProfileRepository?=null

    constructor(application: CareIndiaApplication) :super(application){
        individualProfileRepository = IndividualProfileRepository(application)
    }

}