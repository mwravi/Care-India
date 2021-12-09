package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.PrimaryDataDao
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.PrimaryDataEntity


class PrimaryDataRepository(
    private val primaryDataDao: PrimaryDataDao,
) {

    val primaryData = primaryDataDao.getallPrimarydata()

    fun insert(primaryDataEntity: PrimaryDataEntity) {
        primaryDataDao.insertData(primaryDataEntity)
    }


    internal fun update_primary_first_data(
        PDCGUID: String?,
        Locality: String?,
        Age: Int?,
        Name: String?,
        Gender: Int?,
        Contact: String?,
        Group_Link: Int?,
        SocialCategory: Int?,
        IncomeCertificate: Int?,
        ValidAadhaar: Int?,
        ValidPAN: Int?
    ) {
        primaryDataDao.update_Primary_first_data(
            PDCGUID,
            Locality,
            Age,
            Name,
            Gender,
            Contact,
            Group_Link,
            SocialCategory,
            IncomeCertificate,
            ValidAadhaar,
            ValidPAN
        )
    }


    internal fun update_primary_second_data(
        PDCGUID: String?,
        ValidBank: Int?,
        Business_Interested: Int?,
        Business_details: String?,
        Business_Plan: Int?,
        Business_investment: String?,
        Invest_readiness: Int?,
        Invest_source: Int?,
        Invest_finance: Int,
        Invest_support: String,
        Loan_interested: Int,
        Loan_amount: Int
    ) {
        primaryDataDao.update_primary_second_data(
            PDCGUID,
            ValidBank,
            Business_Interested,
            Business_details,
            Business_Plan,
            Business_investment,
            Invest_readiness,
            Invest_source,
            Invest_finance,
            Invest_support,
            Loan_interested,
            Loan_amount
        )
    }


    internal fun update_primary_third(
        PDCGUID: String,
        Business_type: String,
        Business_registered: Int,
        State_selfemp: String?,
        Loan_availed: Int?,
        Loan_source: String?,
        Financial_assist: Int?,
        Financial_assist_amt: Int?,
        Support_selfemp: String?
    ) {
        primaryDataDao.update_primary_third(
            PDCGUID,
            Business_type,
            Business_registered,
            State_selfemp,
            Loan_availed,
            Loan_source,
            Financial_assist,
            Financial_assist_amt,
            Support_selfemp
        )
    }


    fun delete() {
        primaryDataDao.deletePrimarydata()
    }

    fun getallPrimarydata(): LiveData<List<PrimaryDataEntity>> {
        return primaryDataDao.getallPrimarydata()
    }

    fun delete_record(HHGUID: String) {
        return primaryDataDao.delete_record(HHGUID)
    }

    fun getdatabyGuid(guid: String): LiveData<List<PrimaryDataEntity>> {
        return primaryDataDao.getdatabyGuid(guid)
    }

    fun getdatabyPDCGuid(guid: String): LiveData<List<PrimaryDataEntity>> {
        return primaryDataDao.getdatabyPDCGuid(guid)
    }



}