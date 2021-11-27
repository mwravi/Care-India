package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IndividualProfileViewModel(private val imProfileRepository: IndividualProfileRepository) :
    BaseViewModel() {

    var validate: Validate? = null

    val imProfileData = imProfileRepository.getallProfiledata()

    val Date = MutableLiveData<String>()
    val CrpName = MutableLiveData<Int>()
    val SuperverCor = MutableLiveData<Int>()
    val HHUID = MutableLiveData<String>()
    val NameRespo = MutableLiveData<String>()
    val Gender = MutableLiveData<Int>()
    val AGE = MutableLiveData<String>()
    val CASTE = MutableLiveData<Int>()
    val MARITAL = MutableLiveData<Int>()
    val CONTACT = MutableLiveData<String>()

    val saveandnextText = MutableLiveData<String>()


    init {
        validate = Validate(mContext)
        saveandnextText.value = "Save & Next"
    }

    fun saveandUpdateCollectiveProfile() {
        val date: String? = Date.value
        val crpname: String? = returnID(CrpName.value, 41).toString()
        val superverCor: String? = returnID(SuperverCor.value, 42).toString()
        val sex: Int? = returnID(Gender.value, 43)
        val caste: Int? = returnID(CASTE.value, 44)
        val merital_s: Int? = returnID(MARITAL.value, 45)
        val age: Int? = Integer.parseInt(AGE.value)
        val contact_no: String? = CONTACT.value
        val hhuid: String? = HHUID.value
        val namerespo: String? = NameRespo.value

        validate!!.SaveSharepreferenceString(AppSP.IndividualProfileGUID, "")
        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) == "") {
            var imProfileGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.IndividualProfileGUID, imProfileGuid)
            //save to appsp
            insert(
                IndividualProfileEntity(
                    0,
                    imProfileGuid,
                    "",
                    "",
                    "",
                    "",
                    0,
                    "",
                    0,
                    date,
                    hhuid,
                    "",
                    namerespo, sex, age, caste, merital_s, contact_no,
                    "", 0, 0,
                    0, 0, 0, 0, "", "","",
                    "", "", 0, 0, "",
                    0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, "", "", 0,
                    "", "", "", 0, "",0,"" +
                            "","",0,"",0, 0)
            )
        } else if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
            //  update()
        }
    }


    fun insert(imProfileEntity: IndividualProfileEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.insert(imProfileEntity)
        }
    }

    fun update(imProfileEntity: IndividualProfileEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.update(imProfileEntity)
        }
    }


    fun returnID(pos: Int?, flag: Int): Int {
        var data: List<MstCommonEntity>? = null
        data =
            imProfileRepository.getmstCommonData(flag)

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