package com.careindia.lifeskills.services.response

import com.careindia.lifeskills.entity.*
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("mstUser")
    var users: List<MstUserEntity>? = null

    @SerializedName("mst_1State")
    var mst_1State: List<MstStateEntity>? = null

    @SerializedName("mst_2District")
    var mst_2District: List<MstDistrictEntity>? = null

    @SerializedName("Mst_3Zone")
    var Mst_3Zone: List<MstZoneEntity>? = null

    @SerializedName("Mst_4Panchayat_Ward")
    var Mst_4Panchayat_Ward: List<MstPanchayat_WardEntity>? = null

    @SerializedName("mst_5Locality")
    var mst_5Locality: List<MstLocalityEntity>? = null

    @SerializedName("mst_9Lookup")
    var mst_9Lookup: List<MstLookupEntity>? = null

    @SerializedName("mstAssessment")
    var mstAssessment: List<MstAssessmentEntity>? = null

    @SerializedName("tblProfileHH")
    var tblProfileHH: List<HouseholdProfileEntity>? = null

    @SerializedName("tblProfileIndividual")
    var tblProfileIndividual: List<IndividualProfileEntity>? = null

    @SerializedName("tblPsychometric")
    var tblPsychometric: List<PsychometricEntity>? = null

    @SerializedName("tblPDC")
    var tblPDC: List<PrimaryDataEntity>? = null

    @SerializedName("tblCollectiveMeeting")
    var tblCollectiveMeeting: List<CollectiveMeetingEntity>? = null

    @SerializedName("tblCollective")
    var tblCollectiveProfile: List<CollectiveEntity>? = null

    @SerializedName("tblCollectiveMember")
    var tblCollectiveMember: List<CollectiveMemberEntity>? = null

    @SerializedName("tblCollectiveProgressTracker")
    var tblCollectiveProgTracker: List<CollectiveProgressTrackerEntity>? = null

    @SerializedName("tblAssessment")
    var tblAssessment: List<AssessmentEntity>? = null

    @SerializedName("tblAssessmentDetail")
    var tblAssessmentDetail: List<AssessmentDetailEntity>? = null

    @SerializedName("tblTraining")
    var tblTraining: List<TrainingEntity>? = null

    @SerializedName("tblParticipantAttendanceDetail")
    var tblParticipantAttendanceDetail: List<ParticipantAttendanceDetailEntity>? = null

    @SerializedName("mstTrainer")
    var mstTrainer: List<MstTrainerEntity>? = null

    @SerializedName("tblTrainingParticipantDetail")
    var tblTrainingParticipantDetail: List<TrainingParticipantDetailEntity>? = null

    @SerializedName("tblBeneficiaryProgress")
    var tblBeneficiaryProgress: List<BeneficiaryEntity>? = null

    @SerializedName("tblBeneficiaryDetail")
    var tblBeneficiaryDetail: List<BeneficiaryDetailEntity>? = null

}