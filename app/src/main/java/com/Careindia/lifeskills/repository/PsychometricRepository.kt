package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.PsychometricDao
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.PsychometricEntity


class PsychometricRepository(private val psychometricDao: PsychometricDao) {


    fun insert(psychometricEntity: PsychometricEntity) {
        psychometricDao.insertPsychometricData(psychometricEntity)
    }

    fun getallPsychometricdata(): LiveData<List<PsychometricEntity>> {
        return psychometricDao.getallPsychometricdata()
    }

    fun getPsychometricbyGuid(guid: String): LiveData<List<PsychometricEntity>> {
        return psychometricDao.getPsychometricbyGuid(guid)
    }

    fun delete(psychometricEntity: PsychometricEntity) {
        return psychometricDao.deletePsychometricdata(psychometricEntity)
    }


    fun gethhProfileData(): LiveData<List<HouseholdProfileEntity>> {
        return psychometricDao.gethhProfileData()
    }

    fun getallIdvdata(idvcode: String): LiveData<List<IndividualProfileEntity>> {
        return psychometricDao.getallIdvdata(idvcode)
    }

    fun getallhhProfiledata(hhcode: String): LiveData<List<IndividualProfileEntity>> {
        return psychometricDao.getallhhProfiledata(hhcode)
    }

    fun getallIdvPrfdata(hhcode: String): List<IndividualProfileEntity> {
        return psychometricDao.getallIdvPrfdata(hhcode)
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
        primary_occ: String?,
        secondary_occ: String?,
        name_community: String?,
        name_shg: String?,
        nature_entrprise: String?,
        contact_no: String?,
        name_crp: String?,
        date: String?,
        updated_on: String?,
        IsEdited:Int
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
        updated_on: String?,
        IsEdited:Int
    ) {
        psychometricDao.updatePsychometricSecData(
            patGUID,
            min_age_appli,
            applicantEdu,
            prefwoman_socialbw,
            prefwoman_ecobw,
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
        updated_on: String?,
        IsEdited:Int
    ) {
        psychometricDao.updatePsychometricThirdData(
            patGUID,
            selfemp_exp,
            yearexp_selfemp,
            stageself_empidea,
            sizeself_empplanned,
            wilinvst_margmny,
            awr_relmarket_selfemp,
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
        updated_on: String?,
        IsEdited:Int
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
            updated_on,
            IsEdited
        )
    }


}