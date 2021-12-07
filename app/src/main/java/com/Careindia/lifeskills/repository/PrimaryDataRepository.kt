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


    internal fun update_hh_first_data(
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
        Gender: Int?,
    ) {
        primaryDataDao.update_Primary_first_data(
            HHGUID,
            CRP_Code,
            FieldCoordinator,
            StateCode,
            DistrictCode,
            ZoneCode,
            Panchayat_Ward,
            PWCode,
            Localitycode,
            Dateform,
            Name,
            Gender
        )
    }


    internal fun update_hh_second_data(
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
    ) {
        primaryDataDao.update_primary_second_data(
            HHGUID,
            No_adults,
            No_adults_M,
            No_adults_F,
            No_adolescent,
            No_adolescent_M,
            No_adolescent_F,
            No_Children,
            No_Children_M,
            No_Children_F
        )
    }


    internal fun updatehh_third(
        HHGUID: String?,
        No_Earningmembers: Int?,
        No_Earningmembers_M: Int?,
        No_Earningmembers_F: Int?,
        Dwelling_type: Int?,
        Dwelling_Oth: String?,
        Dwelling_Registered: Int?,
        Type_Ration: Int?,
        other_ration:String
    ) {
        primaryDataDao.update_primary_third(
            HHGUID,
            No_Earningmembers,
            No_Earningmembers_M,
            No_Earningmembers_F,
            Dwelling_type,
            Dwelling_Oth,
            Dwelling_Registered,
            Type_Ration,
            other_ration
        )
    }



    fun delete() {
        primaryDataDao.deletePrimarydata()
    }

    fun getallPrimarydata(): LiveData<List<PrimaryDataEntity>> {
        return primaryDataDao.getallPrimarydata()
    }

    fun delete_record(HHGUID:String) {
        return primaryDataDao.delete_record(HHGUID)
    }

    fun gethhdatabyGuid(guid:String): LiveData<List<PrimaryDataEntity>> {
        return primaryDataDao.getdatabyGuid(guid)
    }


}