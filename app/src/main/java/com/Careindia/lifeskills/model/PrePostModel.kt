package com.careindia.lifeskills.model

data class PrePostModel(
    var trainingid: Int,
    var Male: Int,
    var Female: Int,
    var Transgender: Int,
    var StartTime: String,
    var EndTime: String,
    var VenuOfTraning: String,
    var BatchID: Int,
    var ModuleID: Int,
    var ProcessID: Int?=0,
    var SPOID: String,
    var TrainerID: String,
    var DateoftrainingFrom: Long,
    var DateoftrainingTo: Long
)
