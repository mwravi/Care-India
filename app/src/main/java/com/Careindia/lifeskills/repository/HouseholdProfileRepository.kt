package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import androidx.room.Query
import com.careindia.lifeskills.dao.HouseholdProfileDao
import com.careindia.lifeskills.dao.MstDistrictDao
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity


class HouseholdProfileRepository(
    private val hhdao: HouseholdProfileDao,
    private val mstDistrictDao: MstDistrictDao
) {

    val hhProfileData = hhdao.getallHHdata()
    fun insert(hhProfileEntity: HouseholdProfileEntity) {
        hhdao.insertData(hhProfileEntity)
    }


    internal fun update_hh_first_data(
        HHGUID: String,
        CRP_Code: Int,
        FieldCoordinator: Int,
        StateCode: Int?,
        DistrictCode: Int?,
        ZoneCode: Int?,
        Panchayat_Ward: Int?,
        PWCode: String?,
        Localitycode: String?,
        Dateform: String?,
        Name: String?,
        Gender: Int?,
        iUserID:Int,
        IsEdited:Int,
        initials:String?
    ) {
        hhdao.update_hh_first_data(
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
            Gender,
            iUserID,
            IsEdited,
            initials

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
        No_Children_F: Int?,
        iUserID:Int,
        IsEdited:Int
    ) {
        hhdao.update_hh_second_data(
            HHGUID,
            No_adults,
            No_adults_M,
            No_adults_F,
            No_adolescent,
            No_adolescent_M,
            No_adolescent_F,
            No_Children,
            No_Children_M,
            No_Children_F,
            iUserID,
            IsEdited
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
        other_ration:String,
        iUserID:Int,
        IsEdited:Int
    ) {
        hhdao.updatehh_third(
            HHGUID,
            No_Earningmembers,
            No_Earningmembers_M,
            No_Earningmembers_F,
            Dwelling_type,
            Dwelling_Oth,
            Dwelling_Registered,
            Type_Ration,
            other_ration,
            iUserID,
            IsEdited
        )
    }



    fun delete() {
        hhdao.deleteProfiledata()
    }

    fun getallhhProfiledata(): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.getallhhProfiledata()
    }

    fun deletehh_record(HHGUID:String) {
        return hhdao.deletehh_record(HHGUID)
    }

    fun gethhdatabyGuid(guid:String): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.gethhdatabyGuid(guid)
    }

    fun getMstDistrict(StateCode:Int): LiveData<List<MstDistrictEntity>> {
        return mstDistrictDao!!.getMstDistrict(StateCode)
    }
    fun getMstDist(StateCode:Int): List<MstDistrictEntity> {
        return mstDistrictDao!!.getMstDist(StateCode)
    }
    fun getHHIdData(): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.getHHIdData()
    }

    fun getHHCount(): Int {
        return hhdao.getHHCount()
    }
    fun getIndividualID(HHCode:String): Int {
        return hhdao.getIndividualID(HHCode)
    }

    fun getHHZData(izone:Int): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.getHHZData(izone)
    }
    fun getHHWData(izone:Int,iward:Int): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.getHHWData(izone,iward)
    }

    fun getHHPData(iPanchayat:Int): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.getHHPData(iPanchayat)
    }


}