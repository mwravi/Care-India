package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblPsychometric")
data class PsychometricEntity(
    @PrimaryKey @ColumnInfo(name = "PATGUID") val PATGUID: String,
    @ColumnInfo(name = "HHID") val HHID: String? = "",
    @ColumnInfo(name = "IMID") val IMID: String? = "",
    @ColumnInfo(name = "Name_participant") val Name_participant: String? = "",
    @ColumnInfo(name = "Age_partcipant") val Age_partcipant: Int? = 0,
    @ColumnInfo(name = "Primary_occ") val Primary_occ: String? = "",
    @ColumnInfo(name = "Secondary_occ") val Secondary_occ: String? = "",
    @ColumnInfo(name = "Name_Community") val Name_Community: String? = "",
    @ColumnInfo(name = "SHG_Name") val SHG_Name: String? = "",
    @ColumnInfo(name = "Nature_entrprise") val Nature_entrprise: String?,
    @ColumnInfo(name = "Contact_number") val Contact_number: String? = "",
    @ColumnInfo(name = "Name_CRP") val Name_CRP: String? = "",
    @ColumnInfo(name = "Date") val Date: String? = "",
    @ColumnInfo(name = "min_age_applicant") val min_age_applicant: Int? = 0,
    @ColumnInfo(name = "applicant_edu") val applicant_edu: Int? = 0,
    @ColumnInfo(name = "pref_woman_social_bw") val pref_woman_social_bw: Int? = 0,
    @ColumnInfo(name = "pref_woman_eco_bw") val pref_woman_eco_bw: Int? = 0,
    @ColumnInfo(name = "self_emp_exp") val self_emp_exp: Int? = 0,
    @ColumnInfo(name = "year_exp_self_emp") val year_exp_self_emp: Int? = 0,
    @ColumnInfo(name = "stage_self_emp_idea") val stage_self_emp_idea: Int? = 0,
    @ColumnInfo(name = "size_self_emp_planned") val size_self_emp_planned: Int? = 0,
    @ColumnInfo(name = "wil_invst_marg_mny") val wil_invst_marg_mny: Int? = 0,
    @ColumnInfo(name = "awr_rel_market_self_emp") val awr_rel_market_self_emp: Int? = 0,
    @ColumnInfo(name = "evaluate_risk") val evaluate_risk: Int? = 0,
    @ColumnInfo(name = "income_gen_act_invst") val income_gen_act_invst: Int? = 0,
    @ColumnInfo(name = "income_gen_act_manage") val income_gen_act_manage: Int? = 0,
    @ColumnInfo(name = "woman_ct_good_ent") val woman_ct_good_ent: Int? = 0,
    @ColumnInfo(name = "ent_req_fin_res") val ent_req_fin_res: Int? = 0,
    @ColumnInfo(name = "wil_invst_cap_building") val wil_invst_cap_building: Int? = 0,
    @ColumnInfo(name = "successful_ent") val successful_ent: String? = "",
    @ColumnInfo(name = "others_ent") val others_ent: String? = "",
    @ColumnInfo(name = "Createdby") val Createdby: Int? = 0,
    @ColumnInfo(name = "Createdon") val Createdon: String? = "",
    @ColumnInfo(name = "Updatedby") val Updatedby: Int? = 0,
    @ColumnInfo(name = "Updatedon") val Updatedon: String? = "",
    @ColumnInfo(name = "SyncOn") val SyncOn: String? = "",
    @ColumnInfo(name = "Status") val Status: Int? = 0,
    @ColumnInfo(name = "ActionBy") val ActionBy: Int? = 0,
    @ColumnInfo(name = "IsEdited") val IsEdited: Int?
)
