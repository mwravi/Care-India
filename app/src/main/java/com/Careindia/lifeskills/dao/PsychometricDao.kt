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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPsychometricData(psychometricEntity: List<PsychometricEntity>?)

    @Query("Select * from tblPsychometric")
    fun getallPsychometricdata(): LiveData<List<PsychometricEntity>>

    @Query("Select * from tblPsychometric where isEdited = 1")
    fun getPsychometricAlldata(): List<PsychometricEntity>

    @Query("Select * from tblPsychometric where PATGUID=:PsyGuid")
    fun getPsychometricbyGuid(PsyGuid: String): LiveData<List<PsychometricEntity>>


    @Delete
    fun deletePsychometricdata(psychometricEntity: PsychometricEntity)

    @Query("DELETE from tblPsychometric")
    fun deletePsychometric()

    @Query("update tblPsychometric set Updatedby=:updatedby, Secondary_occ_othr=:secondry_other, Primary_occ_othr=:primery_other, PWCode=:Panchayat1, Panchayat_Ward=:Ward1, ZoneCode=:Zone1, DistrictCode=:District1,SHG_Name=:name_shg, Nature_entrprise=:nature_entrprise, Contact_number =:contact_no,Age_partcipant=:age_partcipant,Primary_occ=:primary_occ, Secondary_occ=:secondary_occ, Name_Community=:name_community, Name_CRP=:name_crp, Name_participant=:name_participant, IMID=:imID, HHID=:hhID,Date =:date,Updatedon=:updated_on,IsEdited=:IsEdited,Status=0 where PATGUID=:patGUID")
    fun updatePsychometricFirstData(
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
    )

    @Query("update tblPsychometric set Updatedby=:updatedby, CastBelong=:cast_belong, pref_woman_eco_bw=:prefwoman_ecobw, pref_woman_social_bw=:prefwoman_socialbw,  min_age_applicant=:min_age_appli, applicant_edu =:applicantEdu,Updatedon=:updated_on,IsEdited=:IsEdited,Status=0 where PATGUID=:patGUID")
    fun updatePsychometricSecData(
        patGUID: String,
        min_age_appli: Int?,
        applicantEdu: Int?,
        prefwoman_socialbw: Int?,
        prefwoman_ecobw: Int?,
        cast_belong: String?,
        updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    )

    @Query("update tblPsychometric set Updatedby=:updatedby, awr_rel_market_self_emp=:awr_relmarket_selfemp, wil_invst_marg_mny=:wilinvst_margmny, size_self_emp_planned=:sizeself_empplanned, stage_self_emp_idea=:stageself_empidea, year_exp_self_emp=:yearexp_selfemp, self_emp_exp=:selfemp_exp,Updatedon=:updated_on,IsEdited=:IsEdited,Status=0 where PATGUID=:patGUID")
    fun updatePsychometricThirdData(
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
    )

    @Query("update tblPsychometric set Updatedby=:updatedby, others_ent=:othersEnt, successful_ent=:successfulEnt, wil_invst_cap_building=:wilinvst_capbuilding, ent_req_fin_res=:entreq_finres, woman_ct_good_ent=:woman_ct_goodent, income_gen_act_manage=:incomegen_actmanage, income_gen_act_invst=:incomegen_actinvst, evaluate_risk=:evaluateRisk,Updatedon=:updated_on,IsEdited=:IsEdited,Status=0 where PATGUID=:patGUID")
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
        updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    )


    @Query("Select * from tblProfileIndividual where HHCode=:hhcode")
    fun getallhhProfiledata(hhcode: String): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileHH")
    fun gethhProfileDataNew(): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileIndividual where IndGUID=:indvGUID")
    fun getallIdvdata(indvGUID: String): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileHH")
    fun gethhProfileData(): LiveData<List<HouseholdProfileEntity>>

    @Query("Select * from tblProfileIndividual")
    fun getImProfileData(): List<IndividualProfileEntity>

    @Query("Select * from tblProfileIndividual where HHGUID=:hhGUID")
    fun getallIdvPrfdata(hhGUID: String): List<IndividualProfileEntity>

    @Query("Select * from tblProfileIndividual where HHCode=:hhcode")
    fun getallIdvPrfdataUpdate(hhcode: String): List<IndividualProfileEntity>

    @Query("Select * from tblProfileHH where Panchayat_Ward=:panchayat")
    fun gethhDataPanchayat(panchayat: Int): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileHH where zoneCode=:zoneCode and Panchayat_Ward=:ward")
    fun gethhDataZone(zoneCode: Int, ward: Int): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileHH where ZoneCode=:ZoneCode and Panchayat_Ward=:WardCode")
    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileHH where Panchayat_Ward=:PanchayatCode")
    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity>

    @Query("Update tblPsychometric set IsEdited=0")
    fun updateIsEdited()


    @Query("Select * from tblPsychometric where DistrictCode=:idiscode and Panchayat_Ward=:iPanchayat")
    fun getIDPDisData(iPanchayat: Int, idiscode: Int): LiveData<List<PsychometricEntity>>

    @Query("Select * from tblPsychometric where IMID=:imGuid")
    fun getPsychoList(imGuid: String): LiveData<List<PsychometricEntity>>


    @Query("Select * from tblPsychometric where DistrictCode=:idis and ZoneCode=:izone and Panchayat_Ward=:iward")
    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<PsychometricEntity>>


    @Query("Select * from tblPsychometric where DistrictCode=:idiscode and ZoneCode=:izone")
    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<PsychometricEntity>>

    @Query("Select * from tblPsychometric where DistrictCode=:idiscode")
    fun getIDDistrictData(idiscode: Int): LiveData<List<PsychometricEntity>>

//    @Query("Select tblProfileIndividual.* from tblProfileIndividual left join tblPsychometric  where tblProfileIndividual.HHGUID=:hhguid and tblProfileIndividual.IndvCode not in (select IMID from tblPsychometric)")
//    fun findIdvPrfdata(hhguid: String): List<IndividualProfileEntity>

    @Query("SELECT *  FROM tblProfileIndividual WHERE HHGUID=:hhguid AND IndGUID not in (select IMID from tblPsychometric)")
    fun findIdvPrfdata(hhguid: String): List<IndividualProfileEntity>

    @Query("Select * from tblProfileIndividual where HHCode=:hhGUID")
    fun getallDataPsychometricdata(hhGUID: String): List<IndividualProfileEntity>

    @Query("Select * from tblProfileIndividual where IndGUID=:indGUID")
    fun getINDIDdata(indGUID: String): List<IndividualProfileEntity>

    @Query("Select * from tblPsychometric where IMID=:indvID")
    fun getPsychodata(indvID: String): List<PsychometricEntity>

    @Query("Select Count(*) from tblPsychometric where IsEdited=1")
    fun getPsychounsyncCount(): String

    @Query("Select Count(*) from tblPsychometric")
    fun getPsychototalCount(): String

    @Query("Update tblPsychometric set Remarks=:remarks, Status=:status where PATGUID =:patguid")
    fun updateStatusData(remarks:String,status: Int, patguid: String)
}