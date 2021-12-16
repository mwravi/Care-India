package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.PrimaryDataDao
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
        CollectionDate: String,
        CommunityName: String,
        Name: String?,
        Age: Int?,
        Gender: Int?,
        Contact: String?,
        Group_Link: Int?,
        SocialCategory: Int?,
        CastIncomeCertificate: Int?,
        ValidAadhaar: Int?,
        ValidPAN: Int?,
        IsEdited:Int
    ) {
        primaryDataDao.update_Primary_first_data(
            PDCGUID,
            Locality,
            CollectionDate,
            CommunityName,
            Name,
            Age,
            Gender,
            Contact,
            Group_Link,
            SocialCategory,
            CastIncomeCertificate,
            ValidAadhaar,
            ValidPAN,
            IsEdited
        )
    }


    internal fun update_primary_second_data(
        PDCGUID: String,
        ValidBank: Int,
        Business_Interested: Int,
        Business_Training: String,
        Business_Plan: Int,
        Business_Investment_Amt: String,
        Invest_readiness: Int,
        Invest_HowMuch: Int,
        Invest_Plan: Int,
        Invest_Plan_Oth: String,
        Financial_Assistance: String,
        Invest_support: Int,
        Invest_support_Oth:String,
        Loan_interested: Int,
        Loan_Source: Int,
        Loan_Source_Oth: String,
        Loan_amount: String,
        IsEdited:Int
    ) {
        primaryDataDao.update_primary_second_data(
            PDCGUID,
            ValidBank,
            Business_Interested,
            Business_Training,
            Business_Plan,
            Business_Investment_Amt,
            Invest_readiness,
            Invest_HowMuch,
            Invest_Plan,
            Invest_Plan_Oth,
            Financial_Assistance,
            Invest_support,
            Invest_support_Oth,
            Loan_interested,
            Loan_Source,
            Loan_Source_Oth,
            Loan_amount,
            IsEdited
        )
    }


    internal fun update_primary_third(
        PDCGUID: String,
        Business_type: String,
        Business_Invest_Source: Int,
        Business_registered: Int,
        Stage_selfemp: Int,
        Loan_availed: Int,
        Loan_availed_from: Int,
        Financial_assist: Int,
        Financial_assist_amt: String,
        Support_Expecting: Int,
        Support_Expecting_Oth:String,
        IsEdited:Int
    ) {
        primaryDataDao.update_primary_third(
            PDCGUID,
            Business_type,
            Business_Invest_Source,
            Business_registered,
            Stage_selfemp,
            Loan_availed,
            Loan_availed_from,
            Financial_assist,
            Financial_assist_amt,
            Support_Expecting,
            Support_Expecting_Oth,
            IsEdited
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