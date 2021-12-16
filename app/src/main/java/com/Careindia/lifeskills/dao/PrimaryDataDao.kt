package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.PrimaryDataEntity

@Dao
interface PrimaryDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(primaryDataEntity: PrimaryDataEntity?)

    @Query("Select * from tblPDC")
    fun getallPrimarydata(): LiveData<List<PrimaryDataEntity>>

    @Query("update tblPDC set Locality =:Locality,CollectionDate=:CollectionDate,CommunityName=:CommunityName,Name=:Name,Age=:Age, Gender=:Gender, Contact=:Contact, Group_Link=:Group_Link,SocialCategory =:SocialCategory,CastIncomeCertificate=:CastIncomeCertificate,ValidAadhaar=:ValidAadhaar,ValidPAN=:ValidPAN,IsEdited=:IsEdited where PDCGUID=:PDCGUID")
    fun update_Primary_first_data(
        PDCGUID: String?,
        Locality: String?,
        CollectionDate:String,
        CommunityName:String,
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
    )


    @Query("update tblPDC set ValidBank =:ValidBank,Business_Interested =:Business_Interested,Business_Training=:Business_Training, Business_Plan=:Business_Plan, Business_Investment_Amt=:Business_Investment_Amt, Invest_readiness=:Invest_readiness,Invest_HowMuch=:Invest_HowMuch,Invest_Plan=:Invest_Plan,Invest_Plan_Oth=:Invest_Plan_Oth,Financial_Assistance=:Financial_Assistance,Invest_support=:Invest_support,Invest_support_Oth=:Invest_support_Oth,Loan_interested=:Loan_interested,Loan_Source=:Loan_Source,Loan_Source_Oth=:Loan_Source_Oth,Loan_amount=:Loan_amount,IsEdited=:IsEdited where PDCGUID=:PDCGUID")
    fun update_primary_second_data(
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
    )

    @Query("update tblPDC set Business_type =:Business_type,Business_Invest_Source=:Business_Invest_Source,Business_registered =:Business_registered,Stage_Self_Emp=:Stage_selfemp, Loan_availed=:Loan_availed, Loan_availed_from=:Loan_availed_from, Financial_assist=:Financial_assist, Financial_assist_amt=:Financial_assist_amt,Support_Expecting=:Support_Expecting,Support_Expecting_Oth=:Support_Expecting_Oth,IsEdited=:IsEdited  where PDCGUID=:PDCGUID")
    fun update_primary_third(
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

    @Query("Select * from tblPDC")
    fun getPrimaryAlldata(): List<PrimaryDataEntity>
}