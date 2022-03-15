package com.careindia.lifeskills.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class MstFormQuestionModel {
     var QID = 0
     var ModuleID = 0
     var Question: String? = null
     var SeqNo =0
     var LookupID=0
     var IsActive = 0
     var CreatedBy = 0
     var Createdon: String? = null
     var Updatedby = 0
     var UpdatedOn: String? = null
     var PageID = 0
     var PageHeader: String? = null
     var FormID = 0
     var QuestionType = 0
     var QNo: String? = null
     var DependentQuestion: String? = null
     var DependentOption: String? = null
     var Skip: String? = null
     var Link: String? = null




}