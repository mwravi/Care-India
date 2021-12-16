package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblPDC")
data class PrimaryDataEntity(
    @PrimaryKey @ColumnInfo(name = "PDCGUID") val PDCGUID: String,
    @ColumnInfo(name = "IMGUID") val IMGUID: String?="",
    @ColumnInfo(name = "HHGUID") val HHGUID: String?="",
    @ColumnInfo(name = "Locality") val Locality: String? = "",
    @ColumnInfo(name = "CollectionDate") val CollectionDate: String? = "",
    @ColumnInfo(name = "Crpid") val Crpid: Int,
    @ColumnInfo(name = "fcid") val fcid: Int,
    @ColumnInfo(name = "CommunityName") val CommunityName: String?,
    @ColumnInfo(name = "Name") val Name: String? = "",
    @ColumnInfo(name = "Age") val Age: Int? = 0,
    @ColumnInfo(name = "Gender") val Gender: Int?,
    @ColumnInfo(name = "Contact") val Contact: String?,
    @ColumnInfo(name = "Group_Link") val Group_Link: Int?,
    @ColumnInfo(name = "SocialCategory") val SocialCategory: Int?,
    @ColumnInfo(name = "CastIncomeCertificate") val CastIncomeCertificate: Int?,
    @ColumnInfo(name = "ValidAadhaar") val ValidAadhaar: Int?,
    @ColumnInfo(name = "ValidPAN") val ValidPAN: Int?,
    @ColumnInfo(name = "ValidBank") val ValidBank: Int?,
    @ColumnInfo(name = "Business_Interested") val Business_Interested: Int?,
    @ColumnInfo(name = "Business_Training") val Business_Training: String?,
    @ColumnInfo(name = "Business_Plan") val Business_Plan: Int?,
    @ColumnInfo(name = "Business_Investment_Amt") val Business_Investment_Amt: String?,
    @ColumnInfo(name = "Invest_readiness") val Invest_readiness: Int?,
    @ColumnInfo(name = "Invest_HowMuch") val Invest_HowMuch: Int?,
    @ColumnInfo(name = "Invest_Plan") val Invest_Plan: Int?,
    @ColumnInfo(name = "Invest_Plan_Oth") val Invest_Plan_Oth: String?,
    @ColumnInfo(name = "Financial_Assistance") val Financial_Assistance: String?,
    @ColumnInfo(name = "Invest_support") val Invest_support: Int,
    @ColumnInfo(name = "Invest_support_Oth") val Invest_support_Oth: String,
    @ColumnInfo(name = "Loan_interested") val Loan_interested: Int,
    @ColumnInfo(name = "Loan_Source") val Loan_Source: Int,
    @ColumnInfo(name = "Loan_Source_Oth") val Loan_Source_Oth: String,
    @ColumnInfo(name = "Loan_amount") val Loan_amount: String,
    @ColumnInfo(name = "Amt_Invested") val Amt_Invested: String,
    @ColumnInfo(name = "Business_type") val Business_type: String,
    @ColumnInfo(name = "Business_Invest_Source") val Business_Invest_Source: Int,
    @ColumnInfo(name = "Business_registered") val Business_registered: Int,
    @ColumnInfo(name = "Stage_Self_Emp") val Stage_Self_Emp: Int,
    @ColumnInfo(name = "Stage_Self_Emp_Oth") val Stage_Self_Emp_Oth: String,
    @ColumnInfo(name = "Loan_availed") val Loan_availed: Int,
    @ColumnInfo(name = "Loan_availed_from") val Loan_availed_from: Int,
    @ColumnInfo(name = "Financial_assist") val Financial_assist: Int,
    @ColumnInfo(name = "Financial_assist_amt") val Financial_assist_amt: String,
    @ColumnInfo(name = "Support_Expecting") val Support_Expecting: Int,
    @ColumnInfo(name = "Support_Expecting_Oth") val Support_Expecting_Oth: String,
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int?,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: String? = "",
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int?,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn:String?="",
    @ColumnInfo(name = "SyncOn") val SyncOn:String?="",
    @ColumnInfo(name = "Status") val Status: Int?,
    @ColumnInfo(name = "Actionby") val Actionby: Int?,
    @ColumnInfo(name = "IsEdited") val IsEdited: Int?
)

