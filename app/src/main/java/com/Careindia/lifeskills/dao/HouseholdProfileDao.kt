package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity

@Dao
interface HouseholdProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(hhprofileEntity: HouseholdProfileEntity?)

    @Query("Select * from tblProfileHH")
    fun getallHHdata():LiveData<List<HouseholdProfileEntity>>

    @Query("update tblProfileHH set StateCode =:StateCode,DistrictCode =:DistrictCode,ZoneCode=:ZoneCode, Panchayat_Ward=:Panchayat_Ward, PWCode=:PWCode, Localitycode=:Localitycode, Dateform=:Dateform,Name=:Name ,Gender=:Gender,CRPid=:CRP_Code,fcid=:FieldCoordinator where HHGUID=:HHGUID")
    fun update_hh_first_data(
        HHGUID: String,
        CRP_Code:String,
        FieldCoordinator:String,
        StateCode: String?,
        DistrictCode: String?,
        ZoneCode: String?,
        Panchayat_Ward: Int?,
        PWCode: String?,
        Localitycode: String?,
        Dateform: String?,
        Name: String?,
        Gender: Int?
    )

    @Query("update tblProfileHH set No_adults =:No_adults,No_adults_M=:No_adults_M, No_adults_F=:No_adults_F, No_adolescent=:No_adolescent, No_adolescent_M=:No_adolescent_M, No_adolescent_F=:No_adolescent_F,No_Children =:No_Children,No_Children_M=:No_Children_M,No_Children_F=:No_Children_F where HHGUID=:HHGUID")
    fun update_hh_second_data(
        HHGUID: String?,
        No_adults: Int?,
        No_adults_M: Int?,
        No_adults_F: Int?,
        No_adolescent: Int?,
        No_adolescent_M: Int?,
        No_adolescent_F: Int?,
        No_Children: Int?,
        No_Children_M: Int?,
        No_Children_F: Int?
    )

    @Query("update tblProfileHH set No_Earningmembers =:No_Earningmembers,No_Earningmembers_M =:No_Earningmembers_M,No_Earningmembers_F=:No_Earningmembers_F, Dwelling_type=:Dwelling_type, Dwelling_Oth=:Dwelling_Oth, Dwelling_Registered=:Dwelling_Registered,Type_Ration=:Type_Ration,Other_Ration=:other_ration where HHGUID=:HHGUID")
    fun updatehh_third(
        HHGUID: String?,
        No_Earningmembers: Int?,
        No_Earningmembers_M: Int?,
        No_Earningmembers_F: Int?,
        Dwelling_type: Int?,
        Dwelling_Oth: String?,
        Dwelling_Registered: Int?,
        Type_Ration:Int?,
        other_ration:String
    )

    @Query("DELETE from tblprofilehh")
    fun deleteProfiledata()

    @Query("Select * from tblProfileHH")
    fun getallhhProfiledata(): LiveData<List<HouseholdProfileEntity>>

    @Query("DELETE from tblProfileHH where HHGUID=:HHGUID")
    fun deletehh_record(HHGUID:String)

    @Query("select * from tblProfileHH where HHGUID=:HHGUID")
    fun gethhdatabyGuid(HHGUID: String): LiveData<List<HouseholdProfileEntity>>

    @Query("select * from tblProfileHH")
    fun getHHIdData(): LiveData<List<HouseholdProfileEntity>>

    @Query("select Count(HHCode) from tblProfileHH")
    fun getHHCount(): Int
}