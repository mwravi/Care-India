package com.careindia.lifeskills.repository

import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.IndividualProfileDao

class IndividualProfileRepository {

    var imProfileDao: IndividualProfileDao? = null

    constructor(application: CareIndiaApplication) {
        imProfileDao = CareIndiaApplication.database?.imProfileDao()
    }

}