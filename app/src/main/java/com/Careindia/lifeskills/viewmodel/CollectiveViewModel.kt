package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.repository.CollectiveRepository

class CollectiveViewModel : AndroidViewModel {
    var collectiveRepository: CollectiveRepository? = null

    constructor(application: CareIndiaApplication) : super(application) {
        collectiveRepository = CollectiveRepository(application)
    }
}