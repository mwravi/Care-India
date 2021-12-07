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

    @Query("Select * from tblPrimaryData")
    fun getallPrimarydata():LiveData<List<PrimaryDataEntity>>

    @Query("update tblPrimaryData set StateCode =:StateCode,DistrictCode =:DistrictCode,ZoneCode=:ZoneCode, Panchayat_Ward=:Panchayat_Ward, PWCode=:PWCode, Localitycode=:Localitycode, Dateform=:Dateform,Name=:Name ,Gender=:Gender where HHGUID=:HHGUID")
    fun update_Primary_first_data(
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

    @Query("update tblPrimaryData set No_adults =:No_adults,No_adults_M=:No_adults_M, No_adults_F=:No_adults_F, No_adolescent=:No_adolescent, No_adolescent_M=:No_adolescent_M, No_adolescent_F=:No_adolescent_F,No_Children =:No_Children where HHGUID=:HHGUID")
    fun update_primary_second_data(
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

    @Query("update tblPrimaryData set No_adults =:No_Earningmembers,No_adults_M =:No_Earningmembers_M,No_adolescent=:No_Earningmembers_F, No_adolescent_M=:Dwelling_type, No_adolescent_F=:Dwelling_Oth, No_Children=:Dwelling_Registered,No_Children=:Type_Ration,No_Children=:other_ration where HHGUID=:HHGUID")
    fun update_primary_third(
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

    @Query("DELETE from tblPrimaryData")
    fun deletePrimarydata()



    @Query("DELETE from tblPrimaryData where HHGUID=:HHGUID")
    fun delete_record(HHGUID:String)

    @Query("select * from tblPrimaryData where HHGUID=:HHGUID")
    fun getdatabyGuid(HHGUID: String): LiveData<List<PrimaryDataEntity>>



    @Query("select Count(HHCode) from tblPrimaryData")
    fun getHHCount(): Int
}