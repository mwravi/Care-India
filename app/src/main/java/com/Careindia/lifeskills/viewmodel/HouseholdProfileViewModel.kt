package com.careindia.lifeskills.viewmodel


import android.app.Application
import android.content.Context
import android.widget.Spinner
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.Validate
import kotlinx.android.synthetic.main.activity_household_profile_first.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch
import org.koin.dsl.module.applicationContext


class HouseholdProfileViewModel(private val hhrepository: HouseholdProfileRepository): ViewModel() {
    var validate: Validate? = null
    val hhProfileData = hhrepository.hhProfileData


    val Date = MutableLiveData<String?>()
    val itemSelectedPosition = MutableLiveData<Spinner?>()

    val saveandnextText = MutableLiveData<String>()

    init {
//        validate = Validate(HouseholdProfileViewModel)

        saveandnextText.value = "Save & Next"
    }


//    fun datePickerProfile(){
//        validate!!.datePickerwithmindate(
//            validate!!.Daybetweentime("01-01-1990"),
//            et_formfillingDate
//        )
//    }


    fun saveandUpdateHHProfile(){
        val date:String = Date.value!!
        val spinData:Spinner? = itemSelectedPosition.value!!
        insert(HouseholdProfileEntity(0,"",
            spinData.toString(),"","",0,"",0,date,"","",0,0,0,0,0,0,0,0,0,0,0,0,0,"",0,0,0,"",0,"",0,0))
        Date.value = null
    }

    fun insert(hhProfileEntity: HouseholdProfileEntity){
        viewModelScope.launch(Dispatchers.IO) {
            hhrepository.insert(hhProfileEntity)
        }
    }



//    android:onItemSelected="@{(parent,view,pos,id)->viewModel.onSelectItem(parent,view,pos,id)}"
//    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {
////    pos get selected item position
//     view.getText() get lable of selected item
//     parent.getAdapter().getItem(pos) get item by pos
//     parent.getAdapter().getCount() get item count
//     parent.getCount() get item count
//     parent.getSelectedItem() get selected item
//     and other... }
//    }

}