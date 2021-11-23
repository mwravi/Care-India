package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.repository.CollectiveMemberRepository

class CollectiveMemberViewModel : AndroidViewModel {
    var collectiveMemRespository: CollectiveMemberRepository? = null

    constructor(application: CareIndiaApplication) : super(application) {

        collectiveMemRespository = CollectiveMemberRepository(application)
    }
}