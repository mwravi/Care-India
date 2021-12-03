package com.careindia.lifeskills.viewmodel


import android.util.Log
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Entry
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HouseholdProfileViewModel(private val hhrepository: HouseholdProfileRepository) :
    BaseViewModel() {
    var validate: Validate? = null
    var QusAns = ""

    val users: List<MstCommonEntity> = hhrepository.getallData(2)
    var entry: MutableLiveData<Entry> = MutableLiveData()
    val hhProfileData = hhrepository.hhProfileData
    val Date = MutableLiveData<String?>()
    val saveandnextText = MutableLiveData<String>()
    val CrpName = MutableLiveData<Int>()
    val SuperverCor = MutableLiveData<Int>()
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
    val dwelling_place_registered = MutableLiveData<Int>()
    val hhData = hhrepository.getallhhProfiledata()

    init {
        saveandnextText.value = "Save & Next"
        validate = Validate(mContext)
    }


    fun saveandUpdateHHProfile() {

        val date: String = Date.value!!
        val crpname: Int? = returnID(CrpName.value, 1)
        val cordinator: Int? = returnID(SuperverCor.value, 1)
        val district: String? = returnDistrictID(district.value, 10).toString()
        val zone: String? = returnID(zone.value, 4).toString()
        val ward: Int = returnID(ward.value, 5)
        val panchayat: String = returnID(panchayat.value, 6).toString()

        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) == "") {
            var hh_guid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.HHGUID, hh_guid)
            insert(
                HouseholdProfileEntity(
                    hh_guid,
                    crpname,
                    cordinator,
                    "",
                    district,
                    zone,
                    ward,
                    panchayat,
                    Locality.value,
                    date,
                    "",
                    hh_name.value,
                    hh_sex.value,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    "",
                    0,
                    0,
                    0,
                    "",
                    0,
                    "",
                    0,
                    0
                )
            )
        } else {
            updatehh_first(
                validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!,
                crpname!!,
                cordinator!!,
                "",
                district,
                zone,
                ward,
                panchayat,
                Locality.value,
                date,
                "",
                hh_name.value,
                hh_sex.value

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
        CRP_Code:Int,
        FieldCoordinator:Int,
        StateCode: String?,
        DistrictCode: String?,
        ZoneCode: String?,
        Panchayat_Ward: Int?,
        PWCode: String?,
        Localitycode: String?,
        Dateform: String?,
        HHCode: String?,
        Name: String?,
        Gender: Int?
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
                HHCode,
                Name,
                Gender
            )
        }
    }

    fun updatehh_second() {


        updatehhsecond(
            validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!,
            validate!!.RetriveSharepreferenceString(AppSP.HHCODE),
            validate!!.returnIntegerValue(total_adult.value),
            validate!!.returnIntegerValue(adult_male.value),
            validate!!.returnIntegerValue(adult_female.value),
            validate!!.returnIntegerValue(total_adolescent.value),
            validate!!.returnIntegerValue(adolescent_boy.value),
            validate!!.returnIntegerValue(adolescent_girl.value),
            validate!!.returnIntegerValue(total_children.value),
            validate!!.returnIntegerValue(male_children.value),
            validate!!.returnIntegerValue(female_children.value)


            )
    }

    fun updatehhsecond(
        HHGUID: String?,
        HHCode: String?,
        No_adults: Int?,
        No_adults_M: Int?,
        No_adults_F: Int?,
        No_adolescent: Int?,
        No_adolescent_M: Int?,
        No_adolescent_F: Int?,
        No_Children: Int?,
        No_Children_M: Int?,
        No_Children_F: Int?,


        ) {
        viewModelScope.launch(Dispatchers.IO) {
            hhrepository.update_hh_second_data(
                HHGUID,
                HHCode,
                No_adults,
                No_adults_M,
                No_adults_F,
                No_adolescent,
                No_adolescent_M,
                No_adolescent_F,
                No_Children,
                No_Children_M,
                No_Children_F

            )
        }
    }


    fun updatehh_third() {
        val dwellng: Int = returnID(dwelling.value, 38)
        updatehhthird(validate!!.RetriveSharepreferenceString(AppSP.HHGUID),
            validate!!.RetriveSharepreferenceString(AppSP.HHCODE),
            validate!!.returnIntegerValue(total_earning_members.value),
            validate!!.returnIntegerValue(earning_male.value),
            validate!!.returnIntegerValue(earning_female.value),
            dwellng,
            other_dwelling.value,
            dwelling_place_registered.value,
            ration_card.value


        )
    }


    fun updatehhthird(
        HHGUID: String?,
        HHCode: String?,
        No_Earningmembers: Int?,
        No_Earningmembers_M: Int?,
        No_Earningmembers_F: Int?,
        Dwelling_type: Int?,
        Dwelling_Oth: String?,
        Dwelling_Registered: Int?,
        Type_Ration: Int?

    ) {
        viewModelScope.launch(Dispatchers.IO) {
            hhrepository.updatehh_third(
                HHGUID,
                HHCode,
                No_Earningmembers,
                No_Earningmembers_M,
                No_Earningmembers_F,
                Dwelling_type,
                Dwelling_Oth,
                Dwelling_Registered,
                Type_Ration
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

    fun returnID(pos: Int?, flag: Int): Int {
        var data: List<MstCommonEntity>? = null
        data =
            hhrepository.getmstCommonData(flag)

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).id!!
            }
        }
        return id
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

    var langPreferMobile = ""
    var typeEmp = 0
    var isSecondrySource = 0
    fun collectiveProfileThirdData(
        lang_prefer_mobile_use: String,
        rg_type_emp: Int,
        rg_secondary_income: Int
    ) {
        langPreferMobile = lang_prefer_mobile_use
        typeEmp = rg_type_emp
        isSecondrySource = rg_secondary_income
    }


    fun deletehh_record(HHGUID:String) {
        viewModelScope.launch {
            hhrepository.deletehh_record(HHGUID)
        }
    }

    fun gethhdatabyGuid(guid:String): LiveData<List<HouseholdProfileEntity>> {
        return hhrepository.gethhdatabyGuid(guid)
    }

}