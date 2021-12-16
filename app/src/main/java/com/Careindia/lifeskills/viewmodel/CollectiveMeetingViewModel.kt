package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.careindia.lifeskills.entity.CollectiveMeetingEntity
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingSecActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingThirdActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting.*
import kotlinx.android.synthetic.main.activity_collectivemeetingsec.*
import kotlinx.android.synthetic.main.activity_collectivemeetingthird.*
import kotlinx.android.synthetic.main.activity_improfile_two.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectiveMeetingViewModel(private val collectiveMeetingRepository: CollectiveMeetingRepository) :
    BaseViewModel() {
    var validate: Validate? = null
    val collectiveData = collectiveMeetingRepository!!.getAllMemberData()

    init {
        validate = Validate(mContext)

    }



    fun saveandUpdateCollectiveProfile(collectiveMeetingActivity: CollectiveMeetingActivity) {


        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID) == "") {
            val collectiveGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMeetingGUID, collectiveGuid)

            insert(
                CollectiveMeetingEntity(
                    collectiveGuid,
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID),
                    collectiveMeetingActivity.et_date_of_filling.text.toString(),
                    collectiveMeetingActivity.returnHH_GUID(collectiveMeetingActivity.spin_collective_group.selectedItemPosition),
                    collectiveMeetingActivity.et_date_meeting.text.toString(),
                    collectiveMeetingActivity.et_place.text.toString(),
                    collectiveMeetingActivity.et_starttime.text.toString(),
                    collectiveMeetingActivity.et_endtime.text.toString(),
                    validate!!.GetAnswerTypeCheckBoxButtonID(collectiveMeetingActivity.multiCheck_purpose),
                    collectiveMeetingActivity.et_others_purpose.text.toString(),
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
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
                    validate!!.currentdatetime,
                    0,
                    "",
                    0,
                    0,
                    1

                )
            )
        } else if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
            update(
                validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!,
                collectiveMeetingActivity.et_date_of_filling.text.toString(),
                collectiveMeetingActivity.returnHH_GUID(collectiveMeetingActivity.spin_collective_group.selectedItemPosition),
                collectiveMeetingActivity.et_date_meeting.text.toString(),
                collectiveMeetingActivity.et_place.text.toString(),
                collectiveMeetingActivity.et_starttime.text.toString(),
                collectiveMeetingActivity.et_endtime.text.toString(),
                validate!!.GetAnswerTypeCheckBoxButtonID(collectiveMeetingActivity.multiCheck_purpose),
                collectiveMeetingActivity.et_others_purpose.text.toString(),
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
        Dateform: String,
        Col_GUID: String,
        Meeting_date: String,
        Meeting_place: String,
        Meet_start_time: String,
        Meet_end_time: String,
        Meet_purpose: String,
        Meet_purpose_oth: String,
        IsEdited:Int
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
                IsEdited
            )
        }
    }

    fun UpdateWastePicker(collectiveMeetingSecActivity: CollectiveMeetingSecActivity)
    {
        updateSec(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!,
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_male_wp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_male_nwp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_female_wp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_female_nwp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_trans_wp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_trans_nwp.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_male_wp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_male_nwp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_female_wp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_female_nwp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_trans_wp_attended.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingSecActivity.et_trans_nwp_attended.text.toString()),
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
        IsEdited:Int
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
                IsEdited
            )
        }
    }

    fun updateSaving(collectiveMeetingThirdActivity: CollectiveMeetingThirdActivity)
    {
        updateThird(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!,
            collectiveMeetingThirdActivity.et_Savings.text.toString(),
            collectiveMeetingThirdActivity.et_internal_lending.text.toString(),
            collectiveMeetingThirdActivity.et_bank_loans.text.toString(),
            collectiveMeetingThirdActivity.et_schemes_and_services.text.toString(),
            collectiveMeetingThirdActivity.et_entrepreneurial_activities.text.toString(),
            collectiveMeetingThirdActivity.et_training_activities.text.toString(),
            collectiveMeetingThirdActivity.et_leadership.text.toString(),
            collectiveMeetingThirdActivity.et_change.text.toString(),
            collectiveMeetingThirdActivity.et_others.text.toString(),
            validate!!.returnIntegerValue(collectiveMeetingThirdActivity.et_savings.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingThirdActivity.et_loanlending.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingThirdActivity.et_loanschemes.text.toString()),
            validate!!.returnIntegerValue(collectiveMeetingThirdActivity.et_amount.text.toString()),
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
        TotalSavings: Int?,
        LoanAvailed_int: Int?,
        LoanAvailed_ext: Int?,
        Interest_acc: Int?,
        IsEdited:Int
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
                TotalSavings,
                LoanAvailed_int,
                LoanAvailed_ext,
                Interest_acc,
                IsEdited
            )
        }
    }


    fun getCollectivedatabyGuid(guid: String): LiveData<List<CollectiveMeetingEntity>>? {
        return collectiveMeetingRepository?.getCollectiveMemberdatabyGuid(guid)
    }

    fun delete_record(GUID: String) {
        viewModelScope.launch {
            collectiveMeetingRepository.delete_record(GUID)
        }
    }


}