package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveMeetingEntity
import com.careindia.lifeskills.entity.CollectiveProgressTrackerEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.repository.CollectiveProgressTrackerRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingDetailsActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingAttendanceActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingAgendaActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingCollectionActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting.*
import kotlinx.android.synthetic.main.activity_collectivemeeting_fourth.*
import kotlinx.android.synthetic.main.activity_collectivemeetingsec.*
import kotlinx.android.synthetic.main.activity_collectivemeetingthird.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectiveProgressTrackerViewModel:AndroidViewModel {
    var collectiveProgressTrackerRepository: CollectiveProgressTrackerRepository? = null

    constructor(application: Application) : super(application) {
        collectiveProgressTrackerRepository = CollectiveProgressTrackerRepository(application)
    }


    fun insert(collectiveProgressTrackerEntity: CollectiveProgressTrackerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveProgressTrackerRepository!!.insert(collectiveProgressTrackerEntity)
        }
    }

    fun getallCollProgTrackerdata(cuid: String): LiveData<List<CollectiveProgressTrackerEntity>> {
        return collectiveProgressTrackerRepository!!.getallCollProgTrackerdata(cuid)
    }


    fun getLiveCollProgTrackerdatabyGuid(CPT_GUID: String): LiveData<List<CollectiveProgressTrackerEntity>> {
        return collectiveProgressTrackerRepository!!.getLiveCollProgTrackerdatabyGuid(CPT_GUID)
    }

    fun getCollProgTrackerdatabyGuid(CPT_GUID: String): List<CollectiveProgressTrackerEntity> {
        return collectiveProgressTrackerRepository!!.getCollProgTrackerdatabyGuid(CPT_GUID)
    }

    fun deleteCollProgTrackerdata(collectiveProgressTrackerEntity: CollectiveProgressTrackerEntity) {
        return collectiveProgressTrackerRepository!!.deleteCollProgTrackerdata(
            collectiveProgressTrackerEntity
        )
    }

    fun deleteCollProgTrackerdata(CPTGUID: String?){
        return collectiveProgressTrackerRepository!!.deleteCollProgTrackerdata(CPTGUID)
    }

    fun getMstDist(StateCode: Int, DistrictIn: List<String>): List<MstDistrictEntity> {
        return collectiveProgressTrackerRepository!!.getMstDist(StateCode, DistrictIn)
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return collectiveProgressTrackerRepository!!.getMstDist(StateCode)
    }

    fun updatecptFirst(
        CPT_GUID: String,
        PersonName: String,
        Designation: String,
        Date: Long,
        ShgCigName: String,
        collcode: String,
        StateCode: Int,
        DistrictName: Int,
        i: Int,
        i1: Int,
        s: String,
        LocalityName: String,
        Registered: Int,
        RegDate: Long,
        Orgnization: Int,
        CIGCheifName: String,
        UpdatedBy: Int,
        UpdatedOn: Long,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveProgressTrackerRepository!!.updatecptFirst(
                CPT_GUID,
                PersonName,
                Designation,
                Date,
                ShgCigName,
                collcode,
                StateCode,
                DistrictName,
                i,
                i1,
                s,
                LocalityName,
                Registered,
                RegDate,
                Orgnization,
                CIGCheifName,
                UpdatedBy,
                UpdatedOn,
                IsEdited
            )
        }
    }

    fun updatecptSecond(
        CPT_GUID:String,
        Male: String,
        Female: String,
        MaleJoined: String,
        FemaleJoined: String,
        MaleLeft: String,
        FemaleLeft: String,
        MeetingHeld: String,
        MaleParticipated: String,
        FemaleParticipated: String,
        UpdatedBy: Int,
        UpdatedOn: Long,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveProgressTrackerRepository!!.updatecptSecond(
                CPT_GUID,
                Male,
                Female,
                MaleJoined,
                FemaleJoined,
                MaleLeft,
                FemaleLeft,
                MeetingHeld,
                MaleParticipated,
                FemaleParticipated,
                UpdatedBy,
                UpdatedOn,
                IsEdited
            )
        }
    }

    fun updatecptThird(
        CPT_GUID: String,
        Action: Int,
        Representation: Int,
        Voice: Int,
        Elected: Int,
        Rotation: Int,
        Active: Int,
        Book: Int,
        Financial: Int,
        Manage: Int,
        UpdatedBy: Int,
        UpdatedOn: Long,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveProgressTrackerRepository!!.updatecptThird(
                CPT_GUID,
                Action,
                Representation,
                Voice,
                Elected,
                Rotation,
                Active,
                Book,
                Financial,
                Manage,
                UpdatedBy,
                UpdatedOn,
                IsEdited
            )
        }
    }
    internal fun updatecptfourth(
        CPT_GUID: String?,
        MoneyLanding: Int?,
        ReceivingLoanPercentage: Int?,
        RepaymentFreq: Int?,
        AddressIssue: Int?,
        LifeSkill: Int?,
        LifeSkillPlus: Int?,
        EDP: Int?,
        Collectivization: Int?,
        StartingBusiness: Int?,
        BusinessInitiativeTaken: Int?,
        BusinessInitiativeReceived: Int?,
        BusinessInitiativeOthers: String?,
        BusinessInitiativeKind: String?,
        IsEdited: Int,
        UpdatedBy: Int?,
        UpdatedOn: Long?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveProgressTrackerRepository!!.updatecptfourth(
                CPT_GUID,
                MoneyLanding,
                ReceivingLoanPercentage,
                RepaymentFreq,
                AddressIssue,
                LifeSkill,
                LifeSkillPlus,
                EDP,
                Collectivization,
                StartingBusiness,
                BusinessInitiativeTaken,
                BusinessInitiativeReceived,
                BusinessInitiativeOthers,
                BusinessInitiativeKind,
                IsEdited,
                UpdatedBy,
                UpdatedOn
            )
        }
    }

    internal fun updatecptfifth(
        CPT_GUID: String?,
        NoFederation: Int?,
        AlternativeJobs: Int?,
        AJInitiative: Int?,
        AJInitiativeReceived: Int?,
        AJInitiativeReceivedKind: String?,
        LinkageEstablished: Int?,
        PostTraining: Int?,
        NoCapacityBuilding: Int?,
        WomanTrainedBooks: Int?,
        GenderSensitive: Int?,
        IsEdited: Int,
        UpdatedBy: Int?,
        UpdatedOn: Long?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveProgressTrackerRepository!!.updatecptfifth(
                CPT_GUID,
                NoFederation,
                AlternativeJobs,
                AJInitiative,
                AJInitiativeReceived,
                AJInitiativeReceivedKind,
                LinkageEstablished,
                PostTraining,
                NoCapacityBuilding,
                WomanTrainedBooks,
                GenderSensitive,
                IsEdited,
                UpdatedBy,
                UpdatedOn
            )
        }
    }

    internal fun updatecptsixth(
        CPT_GUID: String?,
        CorpusFundStatus: Int?,
        FrequencyRepayment: Int?,
        InternalLoanManage: Int?,
        SelfSustaining: Int?,
        ExposureVisit: Int?,
        NoVisits: Int?,
        OrgName: String?,
        NoMembersParticipated_M: Int?,
        NoMembersParticipated_F: Int?,
        Purpose: String?,
        NoWomenArticulated: Int?,
        NoEcoSystem: Int?,
        IsEdited: Int,
        UpdatedBy: Int?,
        UpdatedOn: Long?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveProgressTrackerRepository!!.updatecptsixth(
                CPT_GUID,
                CorpusFundStatus,
                FrequencyRepayment,
                InternalLoanManage,
                SelfSustaining,
                ExposureVisit,
                NoVisits,
                OrgName,
                NoMembersParticipated_M,
                NoMembersParticipated_F,
                Purpose,
                NoWomenArticulated,
                NoEcoSystem,
                IsEdited,
                UpdatedBy,
                UpdatedOn
            )
        }
    }

    internal fun updatecptseventh(
        CPT_GUID: String?,
        SchemesMobilized: Int?,
        NoSchemesMobilized: Int?,
        BankAccountOpened: Int?,
        NoBankAccountOpened_M: Int?,
        NoBankAccountOpened_F: Int?,
        AccessFinancialRes: Int?,
        IsEdited: Int,
        UpdatedBy: Int?,
        UpdatedOn: Long?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveProgressTrackerRepository!!.updatecptseventh(
                CPT_GUID,
                SchemesMobilized,
                NoSchemesMobilized,
                BankAccountOpened,
                NoBankAccountOpened_M,
                NoBankAccountOpened_F,
                AccessFinancialRes,
                IsEdited,
                UpdatedBy,
                UpdatedOn
            )
        }
    }


    fun getCommunityCount(): Int {
        return collectiveProgressTrackerRepository!!.getCommunityCount()
    }

    fun getCommunityID(CollectiveID: String): Int {
        return collectiveProgressTrackerRepository!!.getCommunityID(CollectiveID)
    }

    fun getCommWData(iDisCode:Int,izone: Int, iward: Int,cuid:String): LiveData<List<CollectiveProgressTrackerEntity>> {
        return collectiveProgressTrackerRepository!!.getCommWData(iDisCode,izone, iward,cuid)
    }

    fun getCommWData(iDisCode: Int, izone: Int,cuid:String): LiveData<List<CollectiveProgressTrackerEntity>> {
        return collectiveProgressTrackerRepository!!.getCommWData(iDisCode, izone,cuid)
    }

    fun getCommWData(iDisCode: Int,cuid:String): LiveData<List<CollectiveProgressTrackerEntity>> {
        return collectiveProgressTrackerRepository!!.getCommWData(iDisCode,cuid)
    }


    fun getCommPData(iDisCode:Int,iPanchayat: Int,cuid:String): LiveData<List<CollectiveProgressTrackerEntity>> {
        return collectiveProgressTrackerRepository!!.getCommPData(iDisCode,iPanchayat,cuid)
    }

    fun getCommPData(iDisCode:Int,cuid:String): LiveData<List<CollectiveProgressTrackerEntity>> {
        return collectiveProgressTrackerRepository!!.getCommPData(iDisCode,cuid)
    }

}