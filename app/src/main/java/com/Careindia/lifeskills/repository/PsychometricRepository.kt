package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.MstDistrictDao
import com.careindia.lifeskills.dao.PsychometricDao
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.PsychometricEntity


class PsychometricRepository(
    private val psychometricDao: PsychometricDao,
    private val mstDistrictDao: MstDistrictDao
) {


    fun insert(psychometricEntity: PsychometricEntity) {
        psychometricDao.insertPsychometricData(psychometricEntity)
    }

    fun getallPsychometricdata(): LiveData<List<PsychometricEntity>> {
        return psychometricDao.getallPsychometricdata()
    }

    fun getPsychometricbyGuid(guid: String): LiveData<List<PsychometricEntity>> {
        return psychometricDao.getPsychometricbyGuid(guid)
    }

    fun getMstDist(StateCode: Int, DistrictIn: List<String>): List<MstDistrictEntity> {
        return mstDistrictDao.getMstDist(StateCode, DistrictIn)
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return mstDistrictDao.getMstDist(StateCode)
    }


    fun gethhDataPanchayat(panchayat: Int): List<HouseholdProfileEntity> {
        return psychometricDao.gethhDataPanchayat(panchayat)
    }

    fun gethhDataZone(zoneCode: Int, ward: Int): List<HouseholdProfileEntity> {
        return psychometricDao.gethhDataZone(zoneCode, ward)
    }

    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity> {
        return psychometricDao.gethhProfileDataWard(ZoneCode, WardCode)
    }

    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity> {
        return psychometricDao.gethhProfileDataPanchayat(PanchayatCode)
    }

    fun delete(psychometricEntity: PsychometricEntity) {
        return psychometricDao.deletePsychometricdata(psychometricEntity)
    }


    fun gethhProfileData(): LiveData<List<HouseholdProfileEntity>> {
        return psychometricDao.gethhProfileData()
    }

    fun getallIdvdata(indvGUID: String): LiveData<List<IndividualProfileEntity>> {
        return psychometricDao.getallIdvdata(indvGUID)
    }

    fun getallhhProfiledata(hhcode: String): LiveData<List<IndividualProfileEntity>> {
        return psychometricDao.getallhhProfiledata(hhcode)
    }

    fun getallIdvPrfdata(hhGUID: String): List<IndividualProfileEntity> {
        return psychometricDao.getallIdvPrfdata(hhGUID)
    }

    fun getallIdvPrfdataUpdate(hhcode: String): List<IndividualProfileEntity> {
        return psychometricDao.getallIdvPrfdataUpdate(hhcode)
    }

    fun gethhProfileDataNew(): List<HouseholdProfileEntity> {
        return psychometricDao.gethhProfileDataNew()
    }

    fun getImProfileData(): List<IndividualProfileEntity> {
        return psychometricDao.getImProfileData()
    }


    internal fun updatePsychometricFirstData(
        patGUID: String,
        hhID: String?,
        imID: String?,
        name_participant: String?,
        age_partcipant: Int?,
        primary_occ: Int?,
        secondary_occ: Int?,
        name_community: String?,
        name_shg: String?,
        nature_entrprise: String?,
        contact_no: String?,
        name_crp: Int?,
        date: Long?,
        District1: Int?,
        Zone1: Int?,
        Ward1: Int?,
        Panchayat1: String?,
        primery_other: String?,
        secondry_other: String?,
        updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        psychometricDao.updatePsychometricFirstData(
            patGUID,
            hhID,
            imID,
            name_participant,
            age_partcipant,
            primary_occ,
            secondary_occ,
            name_community,
            name_shg,
            nature_entrprise,
            contact_no,
            name_crp,
            date,
            District1,
            Zone1,
            Ward1,
            Panchayat1,
            primery_other,
            secondry_other,
            updatedby,
            updated_on,
            IsEdited
        )
    }


    internal fun updatePsychometricSecData(
        patGUID: String,
        min_age_appli: Int?,
        applicantEdu: Int?,
        prefwoman_socialbw: Int?,
        prefwoman_ecobw: Int?,
        cast_belong: String?,
        updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        psychometricDao.updatePsychometricSecData(
            patGUID,
            min_age_appli,
            applicantEdu,
            prefwoman_socialbw,
            prefwoman_ecobw,
            cast_belong,
            updatedby,
            updated_on,
            IsEdited
        )
    }

    internal fun updatePsychometricThirdData(
        patGUID: String,
        selfemp_exp: Int?,
        yearexp_selfemp: Int?,
        stageself_empidea: Int?,
        sizeself_empplanned: Int?,
        wilinvst_margmny: Int?,
        awr_relmarket_selfemp: Int?,
        updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        psychometricDao.updatePsychometricThirdData(
            patGUID,
            selfemp_exp,
            yearexp_selfemp,
            stageself_empidea,
            sizeself_empplanned,
            wilinvst_margmny,
            awr_relmarket_selfemp,
            updatedby,
            updated_on,
            IsEdited
        )
    }

    internal fun updatePsychometricForthData(
        patGUID: String,
        evaluateRisk: Int?,
        incomegen_actinvst: Int?,
        incomegen_actmanage: Int?,
        woman_ct_goodent: Int?,
        entreq_finres: Int?,
        wilinvst_capbuilding: Int?,
        successfulEnt: String?,
        othersEnt: String?,
        updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        psychometricDao.updatePsychometricForthData(
            patGUID,
            evaluateRisk,
            incomegen_actinvst,
            incomegen_actmanage,
            woman_ct_goodent,
            entreq_finres,
            wilinvst_capbuilding,
            successfulEnt,
            othersEnt,
            updatedby,
            updated_on,
            IsEdited
        )
    }

    fun getIDPDisData(iPanchayat: Int, idiscode: Int): LiveData<List<PsychometricEntity>> {
        return psychometricDao.getIDPDisData(iPanchayat, idiscode)
    }
    fun getPsychoList(imGuid: String): LiveData<List<PsychometricEntity>>{
        return psychometricDao.getPsychoList(imGuid)
    }

    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<PsychometricEntity>> {
        return psychometricDao.getIDDisWData(idis, izone, iward)
    }

    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<PsychometricEntity>> {
        return psychometricDao.getIDZData(idiscode, izone)
    }


    fun getIDDistrictData(idiscode: Int): LiveData<List<PsychometricEntity>> {
        return psychometricDao.getIDDistrictData(idiscode)
    }

    fun findIdvPrfdata(hhcode: String): List<IndividualProfileEntity> {
        return psychometricDao.findIdvPrfdata(hhcode)
    }

    fun getallDataPsychometricdata(hhGUID: String): List<IndividualProfileEntity> {
        return psychometricDao.getallDataPsychometricdata(hhGUID)
    }

    fun getINDIDdata(indGUID: String): List<IndividualProfileEntity> {
        return psychometricDao.getINDIDdata(indGUID)
    }

    fun getPsychodata(indvID: String): List<PsychometricEntity> {
        return psychometricDao.getPsychodata(indvID)
    }

}