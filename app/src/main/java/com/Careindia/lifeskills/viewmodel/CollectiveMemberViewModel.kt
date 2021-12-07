package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.CollectiveMemberEntity
import com.careindia.lifeskills.repository.CollectiveMemberRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.collectiveProfile.CollectiveProfileMemberActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectiveMemberViewModel(private val collectiveMemberRepository: CollectiveMemberRepository) :
    BaseViewModel() {
    var validate: Validate? = null


    val Membername = MutableLiveData<String?>()
    val Memberid = MutableLiveData<String?>()
    val Membersex = MutableLiveData<Int?>()
    val Memberage = MutableLiveData<String?>()
    val Contactno = MutableLiveData<String?>()
    val Memberposition = MutableLiveData<String?>()
    val Savingacc = MutableLiveData<Int?>()
    val Aadhar = MutableLiveData<String?>()

    val saveandnextText = MutableLiveData<String>()

    init {
        validate = Validate(mContext)
        saveandnextText.value = "Save & Next"
    }

    var memcollectiveData =
        collectiveMemberRepository.getAllMemberData(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)

    fun savecollectivemember(collectiveProfileMemberActivity: CollectiveProfileMemberActivity) {
        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID) == "") {
            val collectivememGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMemberGUID, collectivememGuid)

            insert(
                CollectiveMemberEntity(
                    validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID)!!,
                    validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID),
                    Memberid.value,
                    Membername.value,
                    collectiveProfileMemberActivity.returnID(
                        Membersex.value!!,
                        1,
                        validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
                    ),
                    validate!!.returnIntegerValue(Memberage.value),
                    Memberposition.value,
                    Savingacc.value,
                    Contactno.value,
                    Aadhar.value,
                    validate!!.returnStringValue(validate!!.currentdatetime),
                    0,
                    "",
                    0,
                    0
                )
            )
        } else {
            update(
                validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID)!!,
                validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
                Memberid.value!!,
                Membername.value!!,
                collectiveProfileMemberActivity.returnID(
                    Membersex.value!!,
                    1,
                    validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
                ),
                validate!!.returnIntegerValue(Memberage.value),
                Memberposition.value!!,
                Savingacc.value!!,
                Contactno.value!!,
                Aadhar.value!!,
                validate!!.RetriveSharepreferenceInt(AppSP.userId),
                validate!!.returnStringValue(validate!!.currentdatetime)
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
        memberId: String,
        memberName: String,
        membersex: Int,
        memberage: Int,
        memberpos: String,
        memberacc: Int,
        contactNo: String,
        aadharNo: String,
        updatedBy: Int,
        updatedOn: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveMemberRepository.update(
                guid,
                collGuid,
                memberId,
                memberName,
                membersex,
                memberage,
                memberpos,
                memberacc,
                contactNo,
                aadharNo,
                updatedBy,
                updatedOn
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
    fun getCommunityCount():Int{
        return collectiveMemberRepository!!.getCommunityCount()
    }
}