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

    @Query("update tblProfileHH set StateCode =:StateCode,DistrictCode =:DistrictCode,ZoneCode=:ZoneCode, Panchayat_Ward=:Panchayat_Ward, PWCode=:PWCode, Localitycode=:Localitycode, Dateform=:Dateform,HHCode =:HHCode,Name=:Name ,Gender=:Gender,CRP_Code=:CRP_Code,FieldCoordinator=:FieldCoordinator where HHGUID=:HHGUID")
    fun update_hh_first_data(
        HHGUID: String,
        CRP_Code:Int,
        FieldCoordinator:Int,
        StateCode: String?,
        DistrictCode: String?,
        ZoneCode: String?,
        Panchayat_Ward: Int?,
        PWCode: String?,
        Localitycode: String?,
        Dateform: String?,
        HHCode: String?,
        Name: String?,
        Gender: Int?
    )

    @Query("update tblProfileHH set HHCode =:HHCode,No_adults =:No_adults,No_adults_M=:No_adults_M, No_adults_F=:No_adults_F, No_adolescent=:No_adolescent, No_adolescent_M=:No_adolescent_M, No_adolescent_F=:No_adolescent_F,No_Children =:No_Children,No_Children_M=:No_Children_M,No_Children_F=:No_Children_F where HHGUID=:HHGUID and HHCode=:HHCode")
    fun update_hh_second_data(
        HHGUID: String?,
        HHCode: String?,
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

    @Query("update tblProfileHH set No_Earningmembers =:No_Earningmembers,No_Earningmembers_M =:No_Earningmembers_M,No_Earningmembers_F=:No_Earningmembers_F, Dwelling_type=:Dwelling_type, Dwelling_Oth=:Dwelling_Oth, Dwelling_Registered=:Dwelling_Registered,Type_Ration=:Type_Ration where HHGUID=:HHGUID and HHCode=:HHCode")
    fun updatehh_third(
        HHGUID: String?,
        HHCode: String?,
        No_Earningmembers: Int?,
        No_Earningmembers_M: Int?,
        No_Earningmembers_F: Int?,
        Dwelling_type: Int?,
        Dwelling_Oth: String?,
        Dwelling_Registered: Int?,
        Type_Ration:Int?
    )

    @Query("DELETE from tblprofilehh")
    fun deleteProfiledata()

    @Query("Select * from tblProfileHH")
    fun getallhhProfiledata(): LiveData<List<HouseholdProfileEntity>>

    @Query("DELETE from tblProfileHH where HHGUID=:HHGUID")
    fun deletehh_record(HHGUID:String)

    @Query("select * from tblProfileHH where HHGUID=:HHGUID")
    fun gethhdatabyGuid(HHGUID: String): LiveData<List<HouseholdProfileEntity>>

}