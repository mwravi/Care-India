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

    @Query("update tblPDC set Locality =:Locality,Age=:Age, Name=:Name, Gender=:Gender, Contact=:Contact, Group_Link=:Group_Link,SocialCategory =:SocialCategory,IncomeCertificate=:IncomeCertificate,ValidAadhaar=:ValidAadhaar,ValidPAN=:ValidPAN where PDCGUID=:PDCGUID")
    fun update_Primary_first_data(
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
    )


    @Query("update tblPDC set ValidBank =:ValidBank,Business_Interested =:Business_Interested,Business_details=:Business_details, Business_Plan=:Business_Plan, Business_investment=:Business_investment, Invest_readiness=:Invest_readiness,Invest_source=:Invest_source,Invest_finance=:Invest_finance,Invest_support=:Invest_support,Loan_interested=:Loan_interested,Loan_amount=:Loan_amount where PDCGUID=:PDCGUID")
    fun update_primary_second_data(
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
    )

    @Query("update tblPDC set Business_type =:Business_type,Business_registered =:Business_registered,State_selfemp=:State_selfemp, Loan_availed=:Loan_availed, Loan_source=:Loan_source, Financial_assist=:Financial_assist, Financial_assist_amt=:Financial_assist_amt ,Support_selfemp=:Support_selfemp where PDCGUID=:PDCGUID")
    fun update_primary_third(
        PDCGUID: String,
        Business_type: String,
        Business_registered: Int,
        State_selfemp: String?,
        Loan_availed: Int?,
        Loan_source: String?,
        Financial_assist: Int?,
        Financial_assist_amt: Int?,
        Support_selfemp: String?

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
}