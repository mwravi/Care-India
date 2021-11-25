package com.careindia.lifeskills.viewmodel


import android.app.Activity
import android.os.DropBoxManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.Entry
import com.careindia.lifeskills.utils.Validate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


 class HouseholdProfileViewModel(private val hhrepository: HouseholdProfileRepository): ViewModel() {
    var validate: Validate? = null

    /* BIND SPINNER DATA TO THESE PROJECTS */
    val users : List<MstCommonEntity> = hhrepository.getallData(2)

    /* BIND SELECTED PROJECT TO THIS VARIABLE */
//     val selectedProject: MstCommonEntity;

    var entry: MutableLiveData<Entry> = MutableLiveData()

    var abcjay = hhrepository.getallData(45)



    val hhProfileData = hhrepository.hhProfileData


    val Date = MutableLiveData<String?>()
    val itemSelectedPosition = MutableLiveData<Spinner?>()

    val saveandnextText = MutableLiveData<String>()



    init {
//        validate = Validate(this)

        saveandnextText.value = "Save & Next"
    }



    fun saveandUpdateHHProfile(){
        val date:String = Date.value!!
        val spindata:String = entry.value.toString()!!
//        val spinData:Spinner? = itemSelectedPosition.value!!
        insert(HouseholdProfileEntity(0,"",
            "","","",0,"",0,date,"",spindata,0,0,0,0,0,0,0,0,0,0,0,0,0,"",0,0,0,"",0,"",0,0))
        Date.value = null
    }

    fun insert(hhProfileEntity: HouseholdProfileEntity){
        viewModelScope.launch(Dispatchers.IO) {
            hhrepository.insert(hhProfileEntity)
        }
    }





    fun fillSpinner(
        activity: Activity,
        spin: Spinner,
        Header: String?,
        data: List<MstCommonEntity>?
    ) {

        val adapter: ArrayAdapter<String?>
        val sValue = arrayOfNulls<String>(data!!.size + 1)
        if (Header != null && Header.length > 0) {
            sValue[0] = Header
        } else {
            sValue[0] = activity.resources.getString(R.string.select)
        }
        for (i in data.indices) {
            sValue[i + 1] = data[i].value!!.trim()
        }
        adapter = ArrayAdapter(
            activity,
            R.layout.my_spinner_space_dashboard, sValue
        )
        adapter.setDropDownViewResource(R.layout.my_spinner_dashboard)
        spin.setAdapter(adapter)
//        spin.adapter = adapter
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