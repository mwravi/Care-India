package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.CollectiveMeetingDao
import com.careindia.lifeskills.entity.CollectiveMeetingEntity


class CollectiveMeetingRepository(private val collectiveMeetingDao: CollectiveMeetingDao) {

    fun insert(collectiveMeetingEntity: CollectiveMeetingEntity) {
        collectiveMeetingDao.insertAllData(collectiveMeetingEntity)
    }

    fun getAllMemberData(): LiveData<List<CollectiveMeetingEntity>> {
        return collectiveMeetingDao.getAllMemberData()
    }

    fun getAllMemberData(Col_GUID: String): LiveData<List<CollectiveMeetingEntity>> {
        return collectiveMeetingDao.getAllMemberData(Col_GUID)
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
        collectiveMeetingDao.updatecollectiveMember(
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
        collectiveMeetingDao.updatecollectiveMeetSec(
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
        collectiveMeetingDao.updatecollectiveMeetThird(
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

    fun getCollectiveMemberdatabyGuid(guid: String): LiveData<List<CollectiveMeetingEntity>> {
        return collectiveMeetingDao.getCollectiveMemberdatabyGuid(guid)
    }

    fun deletemember(collectiveMeetingEntity: CollectiveMeetingEntity) {
        return collectiveMeetingDao.deletemember(collectiveMeetingEntity)
    }

    fun getMemberID(MemberID: String): Int {
        return collectiveMeetingDao!!.getMemberID(MemberID)
    }

    fun delete_record(GUID: String) {
        return collectiveMeetingDao.delete_record(GUID)
    }

}