package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.PrimaryDataEntity

@Dao
interface PrimaryDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(primaryDataEntity: PrimaryDataEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(primaryDataEntity: List<PrimaryDataEntity>?)

    @Query("Select * from tblPDC")
    fun getallPrimarydata(): LiveData<List<PrimaryDataEntity>>

    @Query("update tblPDC set UpdatedBy=:updatedBy, Name_Collective=:collectiveName, UpdatedOn=:updatedOn, DistrictCode=:DistrictCode,ZoneCode=:ZoneCode,Panchayat_Ward=:Panchayat_Ward,Locality =:Locality,CollectionDate=:CollectionDate,CommunityName=:CommunityName,Name=:Name,Age=:Age, Gender=:Gender, Contact=:Contact, Group_Link=:Group_Link,SocialCategory =:SocialCategory,IsEdited=:IsEdited,Status=0 where PDCGUID=:PDCGUID")
    fun update_Primary_first_data(
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
    )


    @Query("update tblPDC set UpdatedBy=:updatedBy, UpdatedOn=:updatedOn,CastIncomeCertificate=:CastIncomeCertificate,ValidAadhaar=:ValidAadhaar,ValidPAN=:ValidPAN,ValidBank =:ValidBank,IsEdited=:IsEdited,Status=0 where PDCGUID=:PDCGUID")
    fun update_primary_second_data(
        PDCGUID: String,
        CastIncomeCertificate: Int?,
        ValidAadhaar: Int?,
        ValidPAN: Int?,
        ValidBank: Int,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    )

    @Query("update tblPDC set UpdatedBy=:updatedBy, Financial_Assistance=:financial_Assistance, Training_skills=:training_skills, Invest_readiness=:Invest_readiness,Invest_HowMuch=:Invest_HowMuch,Invest_Plan=:Invest_Plan,Invest_Plan_Oth=:Invest_Plan_Oth,  Business_Training_Othr=:business_training_othr, Business_Start_Othr=:business_start_othr, Business_Start=:business_start,Training_Days=:training_days, UpdatedOn=:updatedOn, Business_Interested =:Business_Interested,Business_Training=:Business_Training, Business_Plan=:Business_Plan, Business_Investment_Amt=:Business_Investment_Amt,Invest_support=:Invest_support,Invest_support_Oth=:Invest_support_Oth,Loan_Source=:Loan_Source,Loan_Source_Oth=:Loan_Source_Oth,IsEdited=:IsEdited,Status=0  where PDCGUID=:PDCGUID")
    fun update_primary_third(
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
        financial_Assistance: Int,
        Loan_Source: Int,
        Loan_Source_Oth: String,
        Invest_support: Int,
        Invest_support_Oth: String,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    )


    @Query("update tblPDC set UpdatedBy=:updatedBy, Support_Expecting=:Support_Expecting,Support_Expecting_Oth=:Support_Expecting_Oth, Financial_assist_amt=:Financial_assist_amt, Financial_assist=:Financial_assist, Loan_availed_from=:Loan_availed_from, Loan_availed=:Loan_availed, Business_registered =:Business_registered, Stage_Self_Emp=:Stage_selfemp,Stage_Self_Emp_Oth=:stage_self_emp_Oth, Business_Invested_Othr=:business_invested_othr,  UpdatedOn=:updatedOn,   Loan_amount=:Loan_amount,Business_type =:Business_type,Business_Invest_Source=:Business_Invest_Source,IsEdited=:IsEdited,Status=0 where PDCGUID=:PDCGUID")
    fun update_primary_fourth(
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
    )

    @Query("DELETE from tblPDC")
    fun deletePrimarydata()


    @Query("DELETE from tblPDC where HHGUID=:HHGUID")
    fun delete_record(HHGUID: String)

    @Query("select * from tblPDC where HHGUID=:HHGUID")
    fun getdatabyGuid(HHGUID: String): LiveData<List<PrimaryDataEntity>>

    @Query("select * from tblPDC where PDCGUID=:PDCGUID")
    fun getdatabyPDCGuid(PDCGUID: String): LiveData<List<PrimaryDataEntity>>


    @Query("select Count(HHGUID) from tblPDC")
    fun getHHCount(): Int

    @Query("Select * from tblPDC where isEdited = 1")
    fun getPrimaryAlldata(): List<PrimaryDataEntity>

    @Query("Update tblPDC set IsEdited=0")
    fun updateIsEdited()


    @Query("Select * from tblPDC where DistrictCode=:idiscode and Panchayat_Ward=:iPanchayat")
    fun getIDPDisData(iPanchayat: Int, idiscode: Int): LiveData<List<PrimaryDataEntity>>

    @Query("Select * from tblPDC where IMGUID=:imGUID")
    fun getEDPDList(imGUID: String): LiveData<List<PrimaryDataEntity>>

    @Query("Select * from tblPDC where DistrictCode=:idis and ZoneCode=:izone and Panchayat_Ward=:iward")
    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<PrimaryDataEntity>>


    @Query("Select * from tblPDC where DistrictCode=:idiscode and ZoneCode=:izone")
    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<PrimaryDataEntity>>

    @Query("Select * from tblPDC where DistrictCode=:idiscode")
    fun getIDDistrictData(idiscode: Int): LiveData<List<PrimaryDataEntity>>

//    @Query("Select tblProfileIndividual.* from tblProfileIndividual left join tblPDC where tblProfileIndividual.HHGUID=:hhguid and tblProfileIndividual.IndGUID not in (select IMGUID from tblPDC)")
//    fun findIdvPrfdata(hhguid: String): List<IndividualProfileEntity>

    @Query("SELECT *  FROM tblProfileIndividual WHERE HHGUID=:hhguid AND IndGUID not in (select IMGUID from tblPDC)")
    fun findIdvPrfdata(hhguid: String): List<IndividualProfileEntity>

    @Delete
    fun deleteprimary(primaryDataEntity: PrimaryDataEntity)

    @Query("Select * from tblProfileIndividual where IndGUID=:indGUID")
    fun getINDIDdata(indGUID: String): List<IndividualProfileEntity>

    @Query("select Count(*) from tblPDC where IsEdited=1")
    fun getEDPunsyncCount(): String

    @Query("select Count(*) from tblPDC")
    fun getEDPtotalCount(): String

    @Query("Select * from tblPDC where IMGUID=:indGUID")
    fun getEdpIDdata(indGUID: String): List<PrimaryDataEntity>

    @Query("update tblPDC set Business_Name=:Business_Name,Business_Stage_ID=:Business_Stage_ID, Business_Stage_Othr=:Business_Stage_Othr, Investment_Mode=:Investment_Mode, Investment_Mode_Othr=:Investment_Mode_Othr, Self_Amount_Invested=:Self_Amount_Invested, Investment_Mode_Purpose =:Investment_Mode_Purpose, Loan_Repayment=:Loan_Repayment,Loan_Amount_Returned=:Loan_Amount_Returned, Loan_Interest_Returned=:Loan_Interest_Returned, Non_Repayment_Reason=:Non_Repayment_Reason, UpdatedOn=:updatedOn, UpdatedBy=:updatedBy, IsEdited=:IsEdited,Status=0 where PDCGUID=:PDCGUID")
    fun update_primary_fifth(
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
    )

    @Query("update tblPDC set Business_Support_ID=:Business_Support_ID,Business_Support_Othr=:Business_Support_Othr, Asset_Support_Kind=:Asset_Support_Kind, Cost_Asset=:Cost_Asset, Skill_Training_Nature=:Skill_Training_Nature, Skill_Training_Nature_Othr=:Skill_Training_Nature_Othr, Total_Training_Cost =:Total_Training_Cost, Training_Helped_Business=:Training_Helped_Business,Market_Linkage_ID=:Market_Linkage_ID, Market_Linkage_Othr=:Market_Linkage_Othr, Market_Linkage_Amount=:Market_Linkage_Amount,Branding_Support_ID=:Branding_Support_ID, Branding_Support_Othr=:Branding_Support_Othr, Branding_Support_Amount=:Branding_Support_Amount, Expenses_Month=:Expenses_Month, Sales_Month=:Sales_Month, Profit_Month=:Profit_Month, Money_Invested_Month=:Money_Invested_Month, Net_Saving_Month=:Net_Saving_Month, UpdatedOn=:updatedOn, UpdatedBy=:updatedBy, IsEdited=:IsEdited,Status=0 where PDCGUID=:PDCGUID")
    fun update_primary_sixth(
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
    )

    @Query("update tblPDC set Finance_Support_Source=:Finance_Support_Source,Finance_Support_Purpose=:Finance_Support_Purpose, Finance_Support_Amount=:Finance_Support_Amount, Finance_Support_Interest=:Finance_Support_Interest, Business_Running_Smoothly=:Business_Running_Smoothly, Business_Reason_ID=:Business_Reason_ID, Business_Reason_Othr =:Business_Reason_Othr, Successful_Business_Attri=:Successful_Business_Attri, UpdatedOn=:updatedOn, UpdatedBy=:updatedBy, IsEdited=:IsEdited,Status=0 where PDCGUID=:PDCGUID")
    fun update_primary_seventh(
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
    )

    @Query("Update tblPDC set Remarks=:remarks, Status=:status where PDCGUID =:pdcguid")
    fun updateStatusData(remarks:String,status: Int, pdcguid: String)
}