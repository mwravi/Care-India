package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.CollectiveMeetingDao
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveMeetingEntity


class CollectiveMeetingRepository(private val collectiveMeetingDao: CollectiveMeetingDao) {

    fun insert(collectiveMeetingEntity: CollectiveMeetingEntity) {
        collectiveMeetingDao.insertAllData(collectiveMeetingEntity)
    }

    fun getAllMemberData(): LiveData<List<CollectiveMeetingEntity>> {
        return collectiveMeetingDao.getAllMemberData()
    }

    fun getAllMemberDataGuid(Col_GUID: String): LiveData<List<CollectiveMeetingEntity>> {
        return collectiveMeetingDao.getAllMemberData(Col_GUID)
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
            updatedBy,
            updatedOn,
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
            meetingDiscussion_Points,
            updatedBy,
            updatedOn,
            IsEdited
        )
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
        collectiveMeetingDao.updatecollectiveMeetFourth(
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

    fun getCollectiveMemberdatabyGuid(guid: String): LiveData<List<CollectiveMeetingEntity>> {
        return collectiveMeetingDao.getCollectiveMemberdatabyGuid(guid)
    }

    fun getNW_NWP_data(guid: String): List<CollectiveEntity> {
        return collectiveMeetingDao.getNW_NWP_data(guid)
    }

    fun getGroupName(guid: String): String {
        return collectiveMeetingDao.getGroupName(guid)
    }

    fun getCollectiveID(guid: String): String {
        return collectiveMeetingDao.getCollectiveID(guid)
    }

    fun getMaxmtg(guid: String): Int {
        return collectiveMeetingDao.getMaxmtg(guid)
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