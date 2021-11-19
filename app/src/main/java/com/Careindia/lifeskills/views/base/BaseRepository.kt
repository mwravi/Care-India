package com.careindia.lifeskills.ui.base

import com.careindia.lifeskills.network.RestClient


abstract class BaseRepository {

  protected val apiHelper = RestClient.createApiHelper()

}