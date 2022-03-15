package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
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
        Dateform: Long?,
        Name: String?,
        Gender: Int?,
        iUserID:Int,
        IsEdited:Int,
        initials:String?,
        UpdatedBy: Int?,
        UpdatedOn: Long?,
        LandMark: String?,
        PinCode: String?
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
            initials,
            UpdatedBy,
            UpdatedOn,
            LandMark,
            PinCode

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
        No_Earningmembers: Int?,
        No_Earningmembers_M: Int?,
        No_Earningmembers_F: Int?,
        iUserID:Int,
        IsEdited:Int,
        UpdatedBy: Int?,
        UpdatedOn: Long?
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
            No_Earningmembers,
            No_Earningmembers_M,
            No_Earningmembers_F,
            iUserID,
            IsEdited,
            UpdatedBy,
            UpdatedOn
        )
    }


    internal fun updatehh_third(
        HHGUID: String?,
        Dwelling_type: Int?,
        Dwelling_Oth: String?,
        Dwelling_Registered: Int?,
        Type_Ration: Int?,
        other_ration:String,
        iUserID:Int,
        IsEdited:Int,
        UpdatedBy: Int?,
        UpdatedOn: Long?
    ) {
        hhdao.updatehh_third(
            HHGUID,
            Dwelling_type,
            Dwelling_Oth,
            Dwelling_Registered,
            Type_Ration,
            other_ration,
            iUserID,
            IsEdited,
            UpdatedBy,
            UpdatedOn
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

    fun getMstDistrict(StateCode:Int,DistrictIn:List<String>): LiveData<List<MstDistrictEntity>> {
        return mstDistrictDao.getMstDistrict(StateCode,DistrictIn)
    }
    fun getMstDist(StateCode:Int,DistrictIn:List<String>): List<MstDistrictEntity> {
        return mstDistrictDao.getMstDist(StateCode,DistrictIn)
    }

    fun getMstDist(StateCode:Int): List<MstDistrictEntity> {
        return mstDistrictDao.getMstDist(StateCode)
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
    fun getHHWData(iDisCode:Int,izone:Int,iward:Int): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.getHHWData(iDisCode,izone,iward)
    }

    fun getHHWData(iDisCode:Int,iZoneCode:Int): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.getHHWData(iDisCode, iZoneCode)
    }

    fun getHHWData(iDisCode:Int): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.getHHWData(iDisCode)
    }

    fun getHHPData(iDisCode:Int,iPanchayat:Int): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.getHHPData(iDisCode,iPanchayat)
    }

    fun getHHPData(iPanchayat:Int): LiveData<List<HouseholdProfileEntity>> {
        return hhdao.getHHPData(iPanchayat)
    }


}