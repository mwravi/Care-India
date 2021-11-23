package com.careindia.lifeskills.repository

import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.CollectiveDao


class CollectiveRepository {
    var collectiveDao: CollectiveDao?=null

    constructor(application: CareIndiaApplication){
        collectiveDao = CareIndiaApplication.database?.collectiveDao()
    }

}