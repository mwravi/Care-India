package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.HouseholdProfileDao
import com.careindia.lifeskills.dao.MstCommonDao
import com.careindia.lifeskills.dao.MstDistrictDao
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.entity.MstDistrictEntity


class HouseholdProfileRepository(
    private val hhdao: HouseholdProfileDao,
    private val mstCommonDao: MstCommonDao,
    private val mstDistrictDao: MstDistrictDao
) {

    val hhProfileData = hhdao.getallHHdata()
    fun insert(hhProfileEntity: HouseholdProfileEntity) {
        hhdao.insertData(hhProfileEntity)
    }


    internal fun update_hh_first_data(
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
        Gender: Int?,
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
            HHCode,
            Name,
            Gender
        )
    }


    internal fun update_hh_second_data(
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
    ) {
        hhdao.update_hh_second_data(
            HHGUID,
            HHCode,
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
        HHCode: String?,
        No_Earningmembers: Int?,
        No_Earningmembers_M: Int?,
        No_Earningmembers_F: Int?,
        Dwelling_type: Int?,
        Dwelling_Oth: String?,
        Dwelling_Registered: Int?,
        Type_Ration: Int?
    ) {
        hhdao.updatehh_third(
            HHGUID,
            HHCode,
            No_Earningmembers,
            No_Earningmembers_M,
            No_Earningmembers_F,
            Dwelling_type,
            Dwelling_Oth,
            Dwelling_Registered,
            Type_Ration
        )
    }


//    fun getMstCommon(flag: Int): List<MstCommonEntity> {
//        return mstCommonDao!!.getMstCommon(flag)
//    }


    fun getallData(flag: Int): List<MstCommonEntity> {
        return mstCommonDao.getMstCommon(flag)
    }

    fun getmstCommonData(flag: Int): List<MstCommonEntity> {
        return mstCommonDao.getMstCommon(flag)
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

}