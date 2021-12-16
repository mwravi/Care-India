package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.PsychometricEntity


@Dao
interface PsychometricDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPsychometricData(psychometricEntity: PsychometricEntity?)


    @Query("Select * from tblPsychometric")
    fun getallPsychometricdata(): LiveData<List<PsychometricEntity>>

    @Query("Select * from tblPsychometric")
    fun getPsychometricAlldata(): List<PsychometricEntity>

    @Query("Select * from tblPsychometric where PATGUID=:PsyGuid")
    fun getPsychometricbyGuid(PsyGuid: String): LiveData<List<PsychometricEntity>>


    @Delete
    fun deletePsychometricdata(psychometricEntity: PsychometricEntity)


    @Query("update tblPsychometric set SHG_Name=:name_shg, Nature_entrprise=:nature_entrprise, Contact_number =:contact_no,Age_partcipant=:age_partcipant,Primary_occ=:primary_occ, Secondary_occ=:secondary_occ, Name_Community=:name_community, Name_CRP=:name_crp, Name_participant=:name_participant, IMID=:imID, HHID=:hhID,Date =:date,Updatedon=:updated_on,IsEdited=:IsEdited where PATGUID=:patGUID")
    fun updatePsychometricFirstData(
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
    )

    @Query("update tblPsychometric set  pref_woman_eco_bw=:prefwoman_ecobw, pref_woman_social_bw=:prefwoman_socialbw,  min_age_applicant=:min_age_appli, applicant_edu =:applicantEdu,Updatedon=:updated_on,IsEdited=:IsEdited where PATGUID=:patGUID")
    fun updatePsychometricSecData(
        patGUID: String,
        min_age_appli: Int?,
        applicantEdu: Int?,
        prefwoman_socialbw: Int?,
        prefwoman_ecobw: Int?,
        updated_on: String?,
        IsEdited:Int
    )

    @Query("update tblPsychometric set awr_rel_market_self_emp=:awr_relmarket_selfemp, wil_invst_marg_mny=:wilinvst_margmny, size_self_emp_planned=:sizeself_empplanned, stage_self_emp_idea=:stageself_empidea, year_exp_self_emp=:yearexp_selfemp, self_emp_exp=:selfemp_exp,Updatedon=:updated_on,IsEdited=:IsEdited where PATGUID=:patGUID")
    fun updatePsychometricThirdData(
        patGUID: String,
        selfemp_exp: Int?,
        yearexp_selfemp: Int?,
        stageself_empidea: Int?,
        sizeself_empplanned: Int?,
        wilinvst_margmny: Int?,
        awr_relmarket_selfemp: Int?,
        updated_on: String?,
        IsEdited:Int
    )

    @Query("update tblPsychometric set others_ent=:othersEnt, successful_ent=:successfulEnt, wil_invst_cap_building=:wilinvst_capbuilding, ent_req_fin_res=:entreq_finres, woman_ct_good_ent=:woman_ct_goodent, income_gen_act_manage=:incomegen_actmanage, income_gen_act_invst=:incomegen_actinvst, evaluate_risk=:evaluateRisk,Updatedon=:updated_on,IsEdited=:IsEdited where PATGUID=:patGUID")
    fun updatePsychometricForthData(
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
    )


    @Query("Select * from tblProfileIndividual where HHCode=:hhcode")
    fun getallhhProfiledata(hhcode: String): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileHH")
    fun gethhProfileDataNew(): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileIndividual where IndvCode=:indvcode")
    fun getallIdvdata(indvcode: String): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileHH")
    fun gethhProfileData(): LiveData<List<HouseholdProfileEntity>>

    @Query("Select * from tblProfileIndividual")
    fun getImProfileData(): List<IndividualProfileEntity>

    @Query("Select * from tblProfileIndividual where HHCode=:hhcode")
    fun getallIdvPrfdata(hhcode: String): List<IndividualProfileEntity>

}