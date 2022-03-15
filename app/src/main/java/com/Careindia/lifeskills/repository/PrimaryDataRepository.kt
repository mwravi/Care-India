package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.MstDistrictDao
import com.careindia.lifeskills.dao.PrimaryDataDao
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.PrimaryDataEntity


class PrimaryDataRepository(
    private val primaryDataDao: PrimaryDataDao,
    private val mstDistrictDao: MstDistrictDao
) {

    val primaryData = primaryDataDao.getallPrimarydata()

    fun insert(primaryDataEntity: PrimaryDataEntity) {
        primaryDataDao.insertData(primaryDataEntity)
    }


    internal fun update_primary_first_data(
        PDCGUID: String?,
        DistrictCode: Int?,
        ZoneCode: Int?,
        Panchayat_Ward: Int?,
        Locality: String?,
        CollectionDate: Long,
        CommunityName: String,
        Name: String?,
        Age: Int?,
        Gender: Int?,
        Contact: String?,
        Group_Link: Int?,
        SocialCategory: Int?,
        updatedBy: Int?,
        updatedOn: Long?,
        collectiveName: String?,
        IsEdited: Int
    ) {
        primaryDataDao.update_Primary_first_data(
            PDCGUID,
            DistrictCode,
            ZoneCode,
            Panchayat_Ward,
            Locality,
            CollectionDate,
            CommunityName,
            Name,
            Age,
            Gender,
            Contact,
            Group_Link,
            SocialCategory,
            updatedBy,
            updatedOn,
            collectiveName,
            IsEdited
        )
    }


    internal fun update_primary_second_data(
        PDCGUID: String,
        CastIncomeCertificate: Int?,
        ValidAadhaar: Int?,
        ValidPAN: Int?,
        ValidBank: Int,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        primaryDataDao.update_primary_second_data(
            PDCGUID,
            CastIncomeCertificate,
            ValidAadhaar,
            ValidPAN,
            ValidBank,
            updatedBy,
            updatedOn,
            IsEdited
        )
    }


    internal fun update_primary_fourth(
        PDCGUID: String,
        Stage_selfemp: Int,
        stage_self_emp_Oth: String,
        Business_type: String,
        business_invested_othr: String,
        Loan_amount: String,
        Business_registered: Int,
        Business_Invest_Source: Int,
        Loan_availed: Int,
        Loan_availed_from: Int,
        Financial_assist: Int,
        Financial_assist_amt: Int,
        Support_Expecting: String,
        Support_Expecting_Oth: String,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        primaryDataDao.update_primary_fourth(
            PDCGUID,
            Stage_selfemp,
            stage_self_emp_Oth,
            Business_type,
            business_invested_othr,
            Loan_amount,
            Business_registered,
            Business_Invest_Source,
            Loan_availed,
            Loan_availed_from,
            Financial_assist,
            Financial_assist_amt,
            Support_Expecting,
            Support_Expecting_Oth,
            updatedBy,
            updatedOn,
            IsEdited
        )
    }


    internal fun update_primary_third(
        PDCGUID: String,
        Business_Interested: Int,
        business_start: String,
        business_start_othr: String,
        training_skills: Int,
        Business_Training: String,
        business_training_othr: String,
        Business_Investment_Amt: String,
        training_days: String,
        Business_Plan: Int,
        Invest_readiness: Int,
        Invest_Plan: String,
        Invest_Plan_Oth: String,
        Invest_HowMuch: Int,
        Financial_Assistance: Int,
        Loan_Source: Int,
        Loan_Source_Oth: String,
        Invest_support: Int,
        Invest_support_Oth: String,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        primaryDataDao.update_primary_third(
            PDCGUID,
            Business_Interested,
            business_start,
            business_start_othr,
            training_skills,
            Business_Training,
            business_training_othr,
            Business_Investment_Amt,
            training_days,
            Business_Plan,
            Invest_readiness,
            Invest_Plan,
            Invest_Plan_Oth,
            Invest_HowMuch,
            Financial_Assistance,
            Loan_Source,
            Loan_Source_Oth,
            Invest_support,
            Invest_support_Oth,
            updatedBy,
            updatedOn,
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

    fun getMstDist(StateCode: Int, DistrictIn: List<String>): List<MstDistrictEntity> {
        return mstDistrictDao.getMstDist(StateCode, DistrictIn)
    }

    fun getIDPDisData(iPanchayat: Int, idiscode: Int): LiveData<List<PrimaryDataEntity>> {
        return primaryDataDao.getIDPDisData(iPanchayat, idiscode)
    }
    fun getEDPDList(imGUID: String): LiveData<List<PrimaryDataEntity>> {
        return primaryDataDao.getEDPDList(imGUID)
    }

    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<PrimaryDataEntity>> {
        return primaryDataDao.getIDDisWData(idis, izone, iward)
    }

    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<PrimaryDataEntity>> {
        return primaryDataDao.getIDZData(idiscode, izone)
    }


    fun getIDDistrictData(idiscode: Int): LiveData<List<PrimaryDataEntity>> {
        return primaryDataDao.getIDDistrictData(idiscode)
    }

    fun delete(primaryDataEntity: PrimaryDataEntity) {
        return primaryDataDao.deleteprimary(primaryDataEntity)
    }

    fun getINDIDdata(indGUID: String): List<IndividualProfileEntity> {
        return primaryDataDao.getINDIDdata(indGUID)
    }

    fun getEdpIDdata(indGUID: String): List<PrimaryDataEntity> {
        return primaryDataDao.getEdpIDdata(indGUID)
    }


    internal fun update_primary_fifth(
        PDCGUID: String,
        Business_Name: String,
        Business_Stage_ID: Int,
        Business_Stage_Othr: String,
        Investment_Mode: String,
        Investment_Mode_Othr: String,
        Self_Amount_Invested: Int,
        Investment_Mode_Purpose: String,
        Loan_Repayment: Int,
        Loan_Amount_Returned: Int,
        Loan_Interest_Returned: Int,
        Non_Repayment_Reason: String,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        primaryDataDao.update_primary_fifth(
            PDCGUID,
            Business_Name,
            Business_Stage_ID,
            Business_Stage_Othr,
            Investment_Mode,
            Investment_Mode_Othr,
            Self_Amount_Invested,
            Investment_Mode_Purpose,
            Loan_Repayment,
            Loan_Amount_Returned,
            Loan_Interest_Returned,
            Non_Repayment_Reason,
            updatedBy,
            updatedOn,
            IsEdited
        )
    }

    internal fun update_primary_sixth(
        PDCGUID: String,
        Business_Support_ID: Int,
        Business_Support_Othr: String,
        Asset_Support_Kind: String,
        Cost_Asset: String,
        Skill_Training_Nature: String,
        Skill_Training_Nature_Othr: String,
        Total_Training_Cost: Int,
        Training_Helped_Business: String,
        Market_Linkage_ID: Int,
        Market_Linkage_Othr: String,
        Market_Linkage_Amount: Int,
        Branding_Support_ID: Int,
        Branding_Support_Othr: String,
        Branding_Support_Amount: Int,
        Expenses_Month: Int,
        Sales_Month: Int,
        Profit_Month: Int,
        Money_Invested_Month: Int,
        Net_Saving_Month: Int,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        primaryDataDao.update_primary_sixth(
            PDCGUID,
            Business_Support_ID,
            Business_Support_Othr,
            Asset_Support_Kind,
            Cost_Asset,
            Skill_Training_Nature,
            Skill_Training_Nature_Othr,
            Total_Training_Cost,
            Training_Helped_Business,
            Market_Linkage_ID,
            Market_Linkage_Othr,
            Market_Linkage_Amount,
            Branding_Support_ID,
            Branding_Support_Othr,
            Branding_Support_Amount,
            Expenses_Month,
            Sales_Month,
            Profit_Month,
            Money_Invested_Month,
            Net_Saving_Month,
            updatedBy,
            updatedOn,
            IsEdited
        )
    }

    internal fun update_primary_seventh(
        PDCGUID: String,
        Finance_Support_Source: String,
        Finance_Support_Purpose: String,
        Finance_Support_Amount: Int,
        Finance_Support_Interest: Int,
        Business_Running_Smoothly: Int,
        Business_Reason_ID: Int,
        Business_Reason_Othr: String,
        Successful_Business_Attri: String,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        primaryDataDao.update_primary_seventh(
            PDCGUID,
            Finance_Support_Source,
            Finance_Support_Purpose,
            Finance_Support_Amount,
            Finance_Support_Interest,
            Business_Running_Smoothly,
            Business_Reason_ID,
            Business_Reason_Othr,
            Successful_Business_Attri,
            updatedBy,
            updatedOn,
            IsEdited
        )
    }

}