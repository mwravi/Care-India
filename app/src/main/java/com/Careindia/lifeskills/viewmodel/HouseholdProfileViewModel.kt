package com.careindia.lifeskills.viewmodel


import android.util.Log
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.householdscreen.HouseholdProfileFirstActivity
import com.careindia.lifeskills.views.householdscreen.HouseholdProfileThirdActivity
import kotlinx.android.synthetic.main.activity_household_profile_first.*
import kotlinx.android.synthetic.main.activity_household_profile_third.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HouseholdProfileViewModel(private val hhrepository: HouseholdProfileRepository) :
    BaseViewModel() {
    var validate: Validate? = null
    var QusAns = ""


    val hhProfileData = hhrepository.hhProfileData
    val Date = MutableLiveData<String?>()
    val saveandnextText = MutableLiveData<String>()
    val CrpName = MutableLiveData<String>()
    val SuperverCor = MutableLiveData<String>()
    val district = MutableLiveData<Int>()
    val zone = MutableLiveData<Int>()
    val ward = MutableLiveData<Int>()
    val panchayat = MutableLiveData<Int>()
    val Locality = MutableLiveData<String>()
    val UniqueID = MutableLiveData<String>()
    val hh_name = MutableLiveData<String>()
    val hh_sex = MutableLiveData<Int>()
    val total_adult = MutableLiveData<String>()
    val adult_male = MutableLiveData<String>()
    val adult_female = MutableLiveData<String>()
    val total_adolescent = MutableLiveData<String>()
    val adolescent_boy = MutableLiveData<String>()
    val adolescent_girl = MutableLiveData<String>()
    val total_children = MutableLiveData<String>()
    val male_children = MutableLiveData<String>()
    val female_children = MutableLiveData<String>()
    val total_earning_members = MutableLiveData<String>()
    val earning_male = MutableLiveData<String>()
    val earning_female = MutableLiveData<String>()
    val other_dwelling = MutableLiveData<String>()
    val dwelling = MutableLiveData<Int>()
    val ration_card = MutableLiveData<Int>()
    val other_ration = MutableLiveData<String>()
    val dwelling_place_registered = MutableLiveData<Int>()
    val hhData = hhrepository.getallhhProfiledata()

    init {
        saveandnextText.value = "Save & Next"
        validate = Validate(mContext)
        CrpName.value = validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name)
        SuperverCor.value = validate!!.RetriveSharepreferenceString(AppSP.FCID_Name)
        // UniqueID.value = getHHCode()

    }


    fun saveandUpdateHHProfile(
        householdProfileFirstActivity: HouseholdProfileFirstActivity,
        initials: String
    ) {
        var Ward1 = 0
        var PWCode = ""
        val date: String = Date.value!!
        val District1 =
            returnDistrictID(district.value, validate!!.RetriveSharepreferenceInt(AppSP.StateCode))
        val Zone1 = householdProfileFirstActivity.returnZoneID(
            zone.value,
            District1
        )
        if (Zone1 > 0) {
            Ward1 = householdProfileFirstActivity.returnWardID(
                ward.value,
                Zone1
            )
            PWCode = "W"
        } else {
            Ward1 = householdProfileFirstActivity.returnPanchayatID(
                panchayat.value,
                District1
            )
            PWCode = "P"
        }

        householdProfileFirstActivity.et_hh_unique_id


        if (validate!!.RetriveSharepreferenceString(AppSP.HHGUID) == "") {
            var hh_guid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.HHGUID, hh_guid)
            insert(
                HouseholdProfileEntity(
                    hh_guid,
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID),
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    District1,
                    Zone1,
                    Ward1,
                    PWCode,
                    Locality.value,
                    date,
                    householdProfileFirstActivity.et_hh_unique_id.text.toString(),
                    hh_name.value,
                    hh_sex.value,
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
                    -1,
                    "",
                    -1,
                    -1,
                    "",
                    validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                    validate!!.currentdatetime,
                    0,
                    "",
                    0,
                    0,
                    1,
                    initials
                )
            )
        } else {
            updatehh_first(
                validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!,
                validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                validate!!.RetriveSharepreferenceInt(AppSP.FCID),
                validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                District1,
                Zone1,
                Ward1,
                PWCode,
                Locality.value,
                date,
                hh_name.value,
                hh_sex.value,
                validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                1,
                initials

            )
        }

    }

    fun insert(hhProfileEntity: HouseholdProfileEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            hhrepository.insert(hhProfileEntity)
        }
    }

    fun updatehh_first(
        HHGUID: String,
        CRP_Code: Int,
        FieldCoordinator: Int,
        StateCode: Int?,
        DistrictCode: Int?,
        ZoneCode: Int?,
        Panchayat_Ward: Int?,
        PWCode: String?,
        Localitycode: String?,
        Dateform: String?,
        Name: String?,
        Gender: Int?,
        iUserID: Int,
        IsEdited:Int,
        initials:String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            hhrepository.update_hh_first_data(
                HHGUID,
                CRP_Code,
                FieldCoordinator,
                StateCode,
                DistrictCode,
                ZoneCode,
                Panchayat_Ward,
                PWCode,
                Localitycode,
                Dateform,
                Name,
                Gender,
                iUserID,
                IsEdited,
                initials
            )
        }
    }

    fun updatehh_second() {


        updatehhsecond(
            validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!,
            validate!!.returnIntegerValue(total_adult.value),
            validate!!.returnIntegerValue(adult_male.value),
            validate!!.returnIntegerValue(adult_female.value),
            validate!!.returnIntegerValue(total_adolescent.value),
            validate!!.returnIntegerValue(adolescent_boy.value),
            validate!!.returnIntegerValue(adolescent_girl.value),
            validate!!.returnIntegerValue(total_children.value),
            validate!!.returnIntegerValue(male_children.value),
            validate!!.returnIntegerValue(female_children.value),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            1

        )
    }

    fun updatehhsecond(
        HHGUID: String?,
        No_adults: Int?,
        No_adults_M: Int?,
        No_adults_F: Int?,
        No_adolescent: Int?,
        No_adolescent_M: Int?,
        No_adolescent_F: Int?,
        No_Children: Int?,
        No_Children_M: Int?,
        No_Children_F: Int?,
        iUserID: Int,
        IsEdited:Int


    ) {
        viewModelScope.launch(Dispatchers.IO) {
            hhrepository.update_hh_second_data(
                HHGUID,
                No_adults,
                No_adults_M,
                No_adults_F,
                No_adolescent,
                No_adolescent_M,
                No_adolescent_F,
                No_Children,
                No_Children_M,
                No_Children_F,
                iUserID,
                IsEdited

            )
        }
    }


    fun updatehh_third(householdProfileThirdActivity: HouseholdProfileThirdActivity) {

        updatehhthird(
            validate!!.RetriveSharepreferenceString(AppSP.HHGUID),
            validate!!.returnIntegerValue(total_earning_members.value),
            validate!!.returnIntegerValue(earning_male.value),
            validate!!.returnIntegerValue(earning_female.value),
            householdProfileThirdActivity.returnID(
                dwelling.value!!,
                2,
                validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
            ),
            other_dwelling.value,
            validate!!.GetAnswerTypeRadioButtonID(householdProfileThirdActivity.rg_dwelling_place_registered),
            householdProfileThirdActivity.returnID(
                ration_card.value!!,
                4,
                validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
            ),
            other_ration.value!!,
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            1


        )
    }


    fun updatehhthird(
        HHGUID: String?,
        No_Earningmembers: Int?,
        No_Earningmembers_M: Int?,
        No_Earningmembers_F: Int?,
        Dwelling_type: Int?,
        Dwelling_Oth: String?,
        Dwelling_Registered: Int?,
        Type_Ration: Int?,
        other_ration: String,
        iUserID: Int,
        IsEdited:Int

    ) {
        viewModelScope.launch(Dispatchers.IO) {
            hhrepository.updatehh_third(
                HHGUID,
                No_Earningmembers,
                No_Earningmembers_M,
                No_Earningmembers_F,
                Dwelling_type,
                Dwelling_Oth,
                Dwelling_Registered,
                Type_Ration,
                other_ration,
                iUserID,
                IsEdited
            )
        }
    }


    fun GetAnswerTypeCheckBoxButtonID(linear: LinearLayout): String {
        QusAns = ""
        for (i in 0 until linear.childCount) {

            val checkbox = linear.getChildAt(i) as CheckBox
            if (checkbox.isChecked) {
                if (QusAns.length == 0) {
                    QusAns = checkbox.id.toString()
                } else {
                    QusAns = (QusAns
                            + ","
                            + checkbox.id.toString())
                }
            }
        }
        Log.d("CAREINDIA ", "onCheckbox: $QusAns")
        return QusAns

    }


    fun returnDistrictID(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        data =
            hhrepository.getMstDist(StateCode)

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).DistrictCode!!

            }
        }
        return id
    }


    fun deletehh_record(HHGUID: String) {
        viewModelScope.launch {
            hhrepository.deletehh_record(HHGUID)
        }
    }

    fun gethhdatabyGuid(guid: String): LiveData<List<HouseholdProfileEntity>> {
        return hhrepository.gethhdatabyGuid(guid)
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return hhrepository.getMstDist(StateCode)
    }

    fun getHHIdData(): LiveData<List<HouseholdProfileEntity>> {
        return hhrepository.getHHIdData()
    }

    fun getHHCount(): Int {
        return hhrepository.getHHCount()
    }

    fun getIndividualID(HHCode:String): Int {
        return hhrepository.getIndividualID(HHCode)
    }

    fun getHHZData(izone: Int): LiveData<List<HouseholdProfileEntity>> {
        return hhrepository.getHHZData(izone)
    }

    fun getHHWData(izone: Int, iward: Int): LiveData<List<HouseholdProfileEntity>> {
        return hhrepository.getHHWData(izone, iward)
    }

    fun getHHPData(iPanchayat: Int): LiveData<List<HouseholdProfileEntity>> {
        return hhrepository.getHHPData(iPanchayat)
    }
}