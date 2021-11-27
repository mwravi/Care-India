package com.careindia.lifeskills.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.collectiveProfile.CollectiveProfileActivitySec
import java.text.SimpleDateFormat
import java.util.*

class CollectiveViewModel(private val collectiveRepository: CollectiveRepository): BaseViewModel() {
    var validate: Validate? = null

    val collectiveData = collectiveRepository.getallCollectivedata()

    val Date = MutableLiveData<String>()
    val CrpName = MutableLiveData<Int>()
    val SfcName = MutableLiveData<Int>()
    val District = MutableLiveData<Int>()
    val ZoneName = MutableLiveData<Int>()
    val WardName = MutableLiveData<Int>()
    val PanchayatName = MutableLiveData<Int>()
    val localityName = MutableLiveData<String>()
    val collectiveId = MutableLiveData<String>()
    val groupName = MutableLiveData<String>()

    val saveandnextText = MutableLiveData<String>()

    init {
        validate = Validate(mContext)
        saveandnextText.value = "Save & Next"
    }

    fun saveandUpdateCollectiveProfile() {
        val date: String? = Date.value
        val crpname: String? = returnID(CrpName.value, 1).toString()
        val sfcname: String? = returnID(SfcName.value, 2).toString()
        val districcode: String? = returnID(District.value, 3).toString()
        val zonename: String? = returnID(ZoneName.value, 4).toString()
        val wardname: String? = returnID(WardName.value, 5).toString()
        val panchayatcode: Int? = returnID(PanchayatName.value, 6)
        val localityname: String? = localityName.value
        val collectiveid: String? = collectiveId.value
        val groupname: String? = groupName.value
        validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID,"")
        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)=="") {
            var collectiveGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID,collectiveGuid)
            //save to appsp
            insert(
                CollectiveEntity(
                    0,
                    collectiveGuid,
                    "",
                    districcode,
                    zonename,
                    panchayatcode,
                    "",
                    localityname,
                    date,
                    collectiveid,
                    groupname,
                    "", 0, 0, "", "", "", 0, 0, 0,
                    0, 0, 0, 0, 0, 0, "", 0, 0, 0, "", 0, 0, "", 0, 0, 0,
                    0, 0, 0, 0, "", "", "", 0, "", "", 0, "", "", 0, "", 0, 0
                )
            )
        }else if(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length>0){
          //  update()
        }
    }

    fun insert(collectiveEntity: CollectiveEntity){
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.insert(collectiveEntity)
        }
    }

    fun update(collectiveEntity: CollectiveEntity){
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.update(collectiveEntity)
        }
    }


    fun returnID(pos: Int?, flag:Int): Int {
        var data: List<MstCommonEntity>? = null
        data =
            collectiveRepository.getmstCommonData(flag)

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).id!!
            }
        }
        return id
    }
}