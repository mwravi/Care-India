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
    @ColumnInfo(name = "Age") val Age: Int? = 0,
    @ColumnInfo(name = "Name") val Name: String? = "",
    @ColumnInfo(name = "Gender") val Gender: Int?,
    @ColumnInfo(name = "Contact") val Contact: String?,
    @ColumnInfo(name = "Group_Link") val Group_Link: Int?,
    @ColumnInfo(name = "SocialCategory") val SocialCategory: Int?,
    @ColumnInfo(name = "IncomeCertificate") val IncomeCertificate: Int?,
    @ColumnInfo(name = "ValidAadhaar") val ValidAadhaar: Int?,
    @ColumnInfo(name = "ValidPAN") val ValidPAN: Int?,
    @ColumnInfo(name = "ValidBank") val ValidBank: Int?,
    @ColumnInfo(name = "Business_Interested") val Business_Interested: Int?,
    @ColumnInfo(name = "Business_details") val Business_details: String?,
    @ColumnInfo(name = "Business_Plan") val Business_Plan: Int?,
    @ColumnInfo(name = "Business_investment") val Business_investment: Int?,
    @ColumnInfo(name = "Invest_readiness") val Invest_readiness: Int?,
    @ColumnInfo(name = "Invest_source") val Invest_source: Int?,
    @ColumnInfo(name = "Invest_finance") val Invest_finance: Int?,
    @ColumnInfo(name = "Invest_support") val Invest_support: String,
    @ColumnInfo(name = "Loan_interested") val Loan_interested: Int,
    @ColumnInfo(name = "Loan_amount") val Loan_amount: Int,
    @ColumnInfo(name = "Business_type") val Business_type: String,
    @ColumnInfo(name = "Business_registered") val Business_registered: Int,
    @ColumnInfo(name = "State_selfemp") val State_selfemp: String,
    @ColumnInfo(name = "Loan_availed") val Loan_availed: Int,
    @ColumnInfo(name = "Loan_source") val Loan_source: String,
    @ColumnInfo(name = "Financial_assist") val Financial_assist: Int,
    @ColumnInfo(name = "Financial_assist_amt") val Financial_assist_amt: Int,
    @ColumnInfo(name = "Support_selfemp") val Support_selfemp: String,
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int?,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: String? = "",
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int?,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn:String?="",
    @ColumnInfo(name = "SyncOn") val SyncOn:String?="",
    @ColumnInfo(name = "Status") val Status: Int?,
    @ColumnInfo(name = "Actionby") val Actionby: Int?
)

