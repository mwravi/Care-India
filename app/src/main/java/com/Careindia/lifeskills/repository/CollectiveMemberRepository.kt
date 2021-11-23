package com.careindia.lifeskills.repository

import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.CollectiveMemberDao


class CollectiveMemberRepository {
    var collectiveMemDao:CollectiveMemberDao?=null

    constructor(application: CareIndiaApplication){
        collectiveMemDao = CareIndiaApplication.database?.collectiveMemDao()
    }
}