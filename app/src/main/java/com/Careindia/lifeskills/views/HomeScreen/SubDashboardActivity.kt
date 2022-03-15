package com.careindia.lifeskills.views.homescreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.entity.BeneficiaryEntity
import com.careindia.lifeskills.entity.PrimaryDataEntity
import com.careindia.lifeskills.entity.PsychometricEntity
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.BeneficiaryViewModel
import com.careindia.lifeskills.viewmodel.CollectiveProgressTrackerViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodel.PsychometricViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.viewmodelfactory.PsychometricViewModelFactory
import com.careindia.lifeskills.views.beneficiaryProgressTracker.BeneficiaryProgressListActivity
import com.careindia.lifeskills.views.improfile.IMProfileListActivity
import com.careindia.lifeskills.views.primarydatascreen.PrimaryDataListActivity
import com.careindia.lifeskills.views.psychometricscreen.PsychometricListActivity
import kotlinx.android.synthetic.main.activity_sub_dashboard.*
import kotlinx.android.synthetic.main.row_edp_prf_items.view.*

class SubDashboardActivity : AppCompatActivity(), View.OnClickListener {
    var validate: Validate? = null
    var listEdp: List<PrimaryDataEntity>? = null
    lateinit var primaryDataViewModel: PrimaryDataViewModel
    var listPsy : List<PsychometricEntity>?= null
    lateinit var psychometricViewModel: PsychometricViewModel
    var listben : List<BeneficiaryEntity>? = null
    lateinit var beneficiaryViewModel: BeneficiaryViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_dashboard)
        tv_title.text = resources.getString(R.string.subdashboard)
        validate = Validate(this)
        validate!!.SaveSharepreferenceInt(AppSP.iLanguageID, 1)
        val primaryDataDao = CareIndiaApplication.database?.primaryDataDao()!!
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val primaryDataRepository =
            PrimaryDataRepository(primaryDataDao, mstDistrictDao)
        primaryDataViewModel = ViewModelProvider(
            this,
            PrimaryDataViewModelFactory(primaryDataRepository)

        )[PrimaryDataViewModel::class.java]
        val psychometricdao = CareIndiaApplication.database?.psychometricDao()
        val psychometricRepository = PsychometricRepository(psychometricdao!!, mstDistrictDao)

        psychometricViewModel = ViewModelProvider(
            this,
            PsychometricViewModelFactory(psychometricRepository)
        )[PsychometricViewModel::class.java]

        beneficiaryViewModel = ViewModelProvider(this).get(BeneficiaryViewModel::class.java)




        listEdp = primaryDataViewModel.getEdpIDdata(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID).toString())
        if (listEdp!!.isNotEmpty()) {
            tbl_date_edp.visibility = View.VISIBLE
            tv_date_edp.setText(validate!!.addDays(listEdp!!.get(0).CollectionDate!!.toInt()))
        }else{
            tbl_date_edp.visibility = View.GONE
        }

        listPsy = psychometricViewModel.getPsychodata(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID).toString())
         if (listPsy!!.isNotEmpty()){
             tbl_date_psyco.visibility = View.VISIBLE
             tv_date_psyco.setText(validate!!.addDays(listPsy!!.get(0).Date!!.toInt()))
         }else{
             tbl_date_psyco.visibility = View.GONE
         }

        listben = beneficiaryViewModel.getBendatabyIndGuid(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID).toString())
        if (listben!!.isNotEmpty()){
            tbl_date_bpt.visibility = View.VISIBLE
            tv_date_bpt.setText(validate!!.addDays(listben!!.get(listben!!.size-1).DateForm!!.toInt()))
        } else{
            tbl_date_bpt.visibility = View.GONE
        }

       applyClickOnView()
    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        ll_edp.setOnClickListener(this)
        ll_psychometric.setOnClickListener(this)
        ll_beneficiary.setOnClickListener(this)
        img_back.setOnClickListener(this)
        img_home.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ll_edp -> {
                validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.WardFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, 0)
                val intent = Intent(this, PrimaryDataListActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.ll_psychometric -> {
                validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.WardFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, 0)
                val intent = Intent(this, PsychometricListActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.ll_beneficiary -> {
                val intent = Intent(this, BeneficiaryProgressListActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.img_back -> {
                val intent = Intent(this, IMProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.img_home -> {
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}