package com.careindia.lifeskills.viewmodel


import android.util.Log
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.Entry
import com.careindia.lifeskills.utils.Validate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.careindia.lifeskills.views.base.BaseViewModel


class HouseholdProfileViewModel(private val hhrepository: HouseholdProfileRepository) :
    BaseViewModel() {
    var validate: Validate? = null
    var QusAns = ""
    /* BIND SPINNER DATA TO THESE PROJECTS */
    val users: List<MstCommonEntity> = hhrepository.getallData(2)
    var entry: MutableLiveData<Entry> = MutableLiveData()

    var getDataList = hhrepository.getallData(45)
    val hhProfileData = hhrepository.hhProfileData


    val Date = MutableLiveData<String?>()

    val saveandnextText = MutableLiveData<String>()

    init {
        saveandnextText.value = "Save & Next"
        validate = Validate(mContext)
    }



    fun saveandUpdateHHProfile() {
        val date: String = Date.value!!

        insert(
            HouseholdProfileEntity(
                0,
                "",
                "",
                "",
                "",
                0,
                "",
                0,
                date,
                "",
                "",
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
        Date.value = null
    }

    fun insert(hhProfileEntity: HouseholdProfileEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            hhrepository.insert(hhProfileEntity)
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


}