package com.careindia.lifeskills.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.CollectiveMemberEntity
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.repository.CollectiveMemberRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.collectiveProfile.CollectiveProfileMemberActivity
import kotlinx.android.synthetic.main.activity_collection_member.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectiveMemberViewModel(private val collectiveMemberRepository: CollectiveMemberRepository) :
    BaseViewModel() {
    var validate: Validate? = null
    val CrpName = MutableLiveData<String>()
    val SuperverCor = MutableLiveData<String>()

    val Membername = MutableLiveData<String?>()
    val Membersex = MutableLiveData<Int?>()
    val Memberage = MutableLiveData<String?>()
    val Contactno = MutableLiveData<String?>()
    val Memberposition = MutableLiveData<String?>()
    val Savingacc = MutableLiveData<Int?>()
//    val Aadhar = MutableLiveData<String?>()

    val saveandnextText = MutableLiveData<String>()

    init {
        validate = Validate(mContext)
        saveandnextText.value = "Save & Close"
        CrpName.value = validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name)
        SuperverCor.value = validate!!.RetriveSharepreferenceString(AppSP.FCID_Name)
    }

    var hhid: String = ""
    var imid: String = ""
    fun collectiveMemberData(
        hhCodess: Any,
        imCode: Any
    ) {
        hhid = hhCodess.toString()
        imid = imCode.toString()

    }


    var memcollectiveData =
        collectiveMemberRepository.getAllMemberData(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)

    fun savecollectivemember(
        collectiveProfileMemberActivity: CollectiveProfileMemberActivity,
        iLanguageID: Int,
        isUrban: Int,
        zoneCode: Int,
        wardCode: Int,
        panchayatCode: Int
    ) {

        var hhGuid: String = ""
        var imguid: String = ""
        if (isUrban == 1) {
            hhGuid = collectiveProfileMemberActivity.returnHH_GUID(
                collectiveProfileMemberActivity.spin_hh_id.selectedItemPosition,
                isUrban,
                zoneCode,
                wardCode
            )
        } else {
            hhGuid = collectiveProfileMemberActivity.returnHH_GUID(
                collectiveProfileMemberActivity.spin_hh_id.selectedItemPosition,
                isUrban,
                0,
                panchayatCode
            )
        }


        imguid = collectiveProfileMemberActivity.returnIMGUIDTest(
            collectiveProfileMemberActivity.spin_im_id.selectedItemPosition,
            hhGuid
        )!!


        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID) == "") {
            val collectivememGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMemberGUID, collectivememGuid)

            insert(
                CollectiveMemberEntity(
                    validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID)!!,
                    validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID),
//                    collectiveProfileMemberActivity.et_member_id.text.toString(),
                    hhGuid,
                    imguid,
                    Membername.value,
                    collectiveProfileMemberActivity.returnID(
                        Membersex.value!!,
                        1,
                        iLanguageID
                    ),
                    collectiveProfileMemberActivity.returnID(
                        collectiveProfileMemberActivity.spin_wp_nwp.selectedItemPosition,
                        61,
                        iLanguageID
                    ),
                    validate!!.returnIntegerValue(Memberage.value),
                    Memberposition.value,
                    validate!!.GetAnswerTypeRadioButtonID(collectiveProfileMemberActivity.rg_savings_account),
                    Contactno.value,
                    collectiveProfileMemberActivity.et_aadhar_card.text.toString(),
                    validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                    0,
                    0,
                    0,
                    0, 1
                )
            )
        } else {
            update(
                validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID)!!,
                validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
//                collectiveProfileMemberActivity.et_member_id.text.toString(),
                hhGuid,
                imguid,
                Membername.value!!,
                collectiveProfileMemberActivity.returnID(
                    Membersex.value!!,
                    1,
                    validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
                ),
                collectiveProfileMemberActivity.returnID(
                    collectiveProfileMemberActivity.spin_wp_nwp.selectedItemPosition,
                    61,
                    iLanguageID
                ),
                validate!!.returnIntegerValue(Memberage.value),
                Memberposition.value!!,
                validate!!.GetAnswerTypeRadioButtonID(collectiveProfileMemberActivity.rg_savings_account),
                Contactno.value!!,
                collectiveProfileMemberActivity.et_aadhar_card.text.toString(),
                validate!!.RetriveSharepreferenceInt(AppSP.userId),
                validate!!.returnStringValue(validate!!.currentdatetime), 1
            )
        }
    }

    fun insert(collectiveMemberEntity: CollectiveMemberEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveMemberRepository.insert(collectiveMemberEntity)
        }
    }

    fun update(
        guid: String,
        collGuid: String,
        hhid: String,
        memberId: String,
        memberName: String,
        membersex: Int,
        Category: Int,
        memberage: Int,
        memberpos: String,
        memberacc: Int,
        contactNo: String,
        aadharNo: String,
        updatedBy: Int,
        updatedOn: String,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveMemberRepository.update(
                guid,
                collGuid,
                hhid,
                memberId,
                memberName,
                membersex,
                Category,
                memberage,
                memberpos,
                memberacc,
                contactNo,
                aadharNo,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }
    }


    fun getCollectiveMemberdatabyGuid(guid: String): LiveData<List<CollectiveMemberEntity>> {
        return collectiveMemberRepository.getCollectiveMemberdatabyGuid(guid)
    }

    fun deletemember(collectiveMemberEntity: CollectiveMemberEntity) {
        viewModelScope.launch {
            collectiveMemberRepository.deletemember(collectiveMemberEntity)
        }
    }

    fun getCommunityCount(): Int {
        return collectiveMemberRepository!!.getCommunityCount()
    }

    fun getMemberID(MemberID: String, collguid: String): Int {
        return collectiveMemberRepository.getMemberID(MemberID, collguid)
    }

    fun getCommunity(guid: String): String {
        return collectiveMemberRepository.getCommunity(guid)
    }

    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity> {
        return collectiveMemberRepository.gethhProfileDataWard(ZoneCode, WardCode)
    }

    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity> {
        return collectiveMemberRepository.gethhProfileDataPanchayat(PanchayatCode)
    }
}