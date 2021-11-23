package com.careindia.lifeskills.repository

import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.HouseholdProfileDao


class HouseholdProfileRepository {
    var hhProfileDao: HouseholdProfileDao? = null

    constructor(application: CareIndiaApplication) {
        hhProfileDao = CareIndiaApplication.database?.hhProfileDao()
    }


}