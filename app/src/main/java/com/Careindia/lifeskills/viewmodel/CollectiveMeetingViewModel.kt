package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveMeetingEntity
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingAgendaActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingAttendanceActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingCollectionActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingDetailsActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting.*
import kotlinx.android.synthetic.main.activity_collectivemeeting_fourth.*
import kotlinx.android.synthetic.main.activity_collectivemeetingsec.*
import kotlinx.android.synthetic.main.activity_collectivemeetingthird.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectiveMeetingViewModel(private val collectiveMeetingRepository: CollectiveMeetingRepository) :
    BaseViewModel() {
    var validate: Validate? = null

    init {
        validate = Validate(mContext)

    }
    val collectiveData = collectiveMeetingRepository!!.getAllMemberDataGuid(validate?.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)


    fun saveandUpdateCollectiveProfile(collectiveMeetingDetailsActivity: CollectiveMeetingDetailsActivity) {


        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID) == "") {
            val collectiveGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMeetingGUID, collectiveGuid)
            val iMaxmtgno = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
                ?.let { collectiveMeetingRepository.getMaxmtg(it) }
            insert(
                CollectiveMeetingEntity(
                    collectiveGuid,
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID),
                    validate!!.getDaysfromdates(
                        collectiveMeetingDetailsActivity.et_date_of_filling.text.toString(),
                        1
                    ),
                    collectiveMeetingDetailsActivity.returnHH_GUID(collectiveMeetingDetailsActivity.spin_collective_group.selectedItemPosition),
                    iMaxmtgno,
                    validate!!.getDaysfromdates(
                        collectiveMeetingDetailsActivity.et_date_meeting.text.toString(),
                        1
                    ),
                    collectiveMeetingDetailsActivity.et_place.text.toString(),
                    collectiveMeetingDetailsActivity.et_starttime.text.toString(),
                    collectiveMeetingDetailsActivity.et_endtime.text.toString(),
                    validate!!.GetAnswerTypeCheckBoxButtonID(collectiveMeetingDetailsActivity.multiCheck_purpose),
                    collectiveMeetingDetailsActivity.et_others_purpose.text.toString(),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    -1,
                    -1,
                    -1,
                    -1,
                    validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                    validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                    0,
                    0,
                    0,
                    0,
                    1, 0, 0, 0, 0, 0, 0,
                    "", ""

                )
            )
        } else if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
            update(
                validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!,
                validate!!.getDaysfromdates(
                    collectiveMeetingDetailsActivity.et_date_of_filling.text.toString(),
                    1
                ),
                collectiveMeetingDetailsActivity.returnHH_GUID(collectiveMeetingDetailsActivity.spin_collective_group.selectedItemPosition),
                validate!!.getDaysfromdates(
                    collectiveMeetingDetailsActivity.et_date_meeting.text.toString(),
                    1
                ),
                collectiveMeetingDetailsActivity.et_place.text.toString(),
                collectiveMeetingDetailsActivity.et_starttime.text.toString(),
                collectiveMeetingDetailsActivity.et_endtime.text.toString(),
                validate!!.GetAnswerTypeCheckBoxButtonID(collectiveMeetingDetailsActivity.multiCheck_purpose),
                collectiveMeetingDetailsActivity.et_others_purpose.text.toString(),
                validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                1
            )
        }
    }


    fun insert(collectiveMeetingEntity: CollectiveMeetingEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveMeetingRepository!!.insert(collectiveMeetingEntity)
        }
    }



    fun update(
        CollMeetGUID: String,
        Dateform: Long,
        Col_GUID: String,
        Meeting_date: Long,
        Meeting_place: String,
        Meet_start_time: String,
        Meet_end_time: String,
        Meet_purpose: String,
        Meet_purpose_oth: String,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveMeetingRepository!!.update(
                CollMeetGUID,
                Dateform,
                Col_GUID,
                Meeting_date,
                Meeting_place,
                Meet_start_time,
                Meet_end_time,
                Meet_purpose,
                Meet_purpose_oth,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }
    }

    fun UpdateWastePicker(collectiveMeetingAttendanceActivity: CollectiveMeetingAttendanceActivity) {
        updateSec(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!,
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_male_wp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_male_nwp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_female_wp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_female_nwp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_trans_wp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_trans_nwp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_male_wp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_male_nwp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_female_wp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_female_nwp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_trans_wp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_trans_nwp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_male_hhm.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_female_hhm.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_trans_hhm.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_male_hhm_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_female_hhm_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingAttendanceActivity.et_trans_hhm_attended.text.toString()),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )
    }

    fun updateSec(
        CollMeetGUID: String,
        Member_male_WP: Int,
        Member_male_NWP: Int,
        Member_female_WP: Int,
        Member_female_NWP: Int,
        Member_Transgender_WP: Int,
        Member_Transgender_NWP: Int,
        Attn_male_WP: Int,
        Attn_male_NWP: Int,
        Attn_female_WP: Int,
        Attn_female_NWP: Int,
        Attn_Transgender_WP: Int,
        Attn_Transgender_NWP: Int,
        Member_male_HHM: Int,
        Member_female_HHM: Int,
        Member_Transgender_HHM: Int,
        Attn_male_HHM: Int,
        Attn_female_HHM: Int,
        Attn_Transgender_HHM: Int,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveMeetingRepository?.updateSec(
                CollMeetGUID,
                Member_male_WP,
                Member_male_NWP,
                Member_female_WP,
                Member_female_NWP,
                Member_Transgender_WP,
                Member_Transgender_NWP,
                Attn_male_WP,
                Attn_male_NWP,
                Attn_female_WP,
                Attn_female_NWP,
                Attn_Transgender_WP,
                Attn_Transgender_NWP,
                Member_male_HHM,
                Member_female_HHM,
                Member_Transgender_HHM,
                Attn_male_HHM,
                Attn_female_HHM,
                Attn_Transgender_HHM,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }
    }

    fun updateSaving(collectiveMeetingAgendaActivity: CollectiveMeetingAgendaActivity) {
        updateThird(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!,
            validate!!.getAgenda(
                collectiveMeetingAgendaActivity.et_Savings1,
                collectiveMeetingAgendaActivity.et_Savings2,
                collectiveMeetingAgendaActivity.et_Savings3,
            ),
            validate!!.getAgenda(
                collectiveMeetingAgendaActivity.et_internal_lending1,
                collectiveMeetingAgendaActivity.et_internal_lending2,
                collectiveMeetingAgendaActivity.et_internal_lending3,
            ),
            validate!!.getAgenda(
                collectiveMeetingAgendaActivity.et_bank_loans1,
                collectiveMeetingAgendaActivity.et_bank_loans2,
                collectiveMeetingAgendaActivity.et_bank_loans3,
            ),
            validate!!.getAgenda(
                collectiveMeetingAgendaActivity.et_schemes_and_services1,
                collectiveMeetingAgendaActivity.et_schemes_and_services2,
                collectiveMeetingAgendaActivity.et_schemes_and_services3,
            ),
            validate!!.getAgenda(
                collectiveMeetingAgendaActivity.et_entrepreneurial_activities1,
                collectiveMeetingAgendaActivity.et_entrepreneurial_activities2,
                collectiveMeetingAgendaActivity.et_entrepreneurial_activities3,
            ),
            validate!!.getAgenda(
                collectiveMeetingAgendaActivity.et_training_activities1,
                collectiveMeetingAgendaActivity.et_training_activities2,
                collectiveMeetingAgendaActivity.et_training_activities3,
            ),
            validate!!.getAgenda(
                collectiveMeetingAgendaActivity.et_leadership1,
                collectiveMeetingAgendaActivity.et_leadership2,
                collectiveMeetingAgendaActivity.et_leadership3,
            ),
            validate!!.getAgenda(
                collectiveMeetingAgendaActivity.et_change1,
                collectiveMeetingAgendaActivity.et_change2,
                collectiveMeetingAgendaActivity.et_change3,
            ),
            validate!!.getAgenda(
                collectiveMeetingAgendaActivity.et_others1,
                collectiveMeetingAgendaActivity.et_others2,
                collectiveMeetingAgendaActivity.et_others3,
            ),
            validate!!.GetAnswerTypeCheckBoxButtonID(collectiveMeetingAgendaActivity.check_meeting_points),

            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )
    }

    fun updateCollection(collectiveMeetingCollectionActivity: CollectiveMeetingCollectionActivity) {
        updateFourth(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!,
            validate!!.returnIntegerValue(collectiveMeetingCollectionActivity.et_savings.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingCollectionActivity.et_loanlending.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingCollectionActivity.et_loanschemes.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingCollectionActivity.et_amount.text.toString()),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )
    }

    fun updateThird(
        CollMeetGUID: String,
        Savings: String?,
        InternalLending: String?,
        BankLoans: String?,
        Schemes_and_services: String?,
        EntrepreneurialActivities: String?,
        TrainingActivities: String?,
        Change_position: String?,
        ChangeGrpMember: String?,
        OtherPoint: String?,
        meetingDiscussion_Points: String?,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveMeetingRepository?.updateThird(
                CollMeetGUID,
                Savings,
                InternalLending,
                BankLoans,
                Schemes_and_services,
                EntrepreneurialActivities,
                TrainingActivities,
                Change_position,
                ChangeGrpMember,
                OtherPoint,
                meetingDiscussion_Points,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }
    }


    fun updateFourth(
        CollMeetGUID: String,
        TotalSavings: Int?,
        LoanAvailed_int: Int?,
        LoanAvailed_ext: Int?,
        Interest_acc: Int?,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveMeetingRepository.updateFourth(
                CollMeetGUID,
                TotalSavings,
                LoanAvailed_int,
                LoanAvailed_ext,
                Interest_acc,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }
    }


    fun getCollectivedatabyGuid(guid: String): LiveData<List<CollectiveMeetingEntity>>? {
        return collectiveMeetingRepository?.getCollectiveMemberdatabyGuid(guid)
    }

    fun getNW_NWP_data(guid: String): List<CollectiveEntity>? {
        return collectiveMeetingRepository.getNW_NWP_data(guid)
    }

    fun getGroupName(guid: String): String {
        return collectiveMeetingRepository.getGroupName(guid)
    }

    fun getCollectiveID(guid: String): String {
        return collectiveMeetingRepository.getCollectiveID(guid)
    }

    fun delete_record(GUID: String) {
        viewModelScope.launch {
            collectiveMeetingRepository.delete_record(GUID)
        }
    }


}