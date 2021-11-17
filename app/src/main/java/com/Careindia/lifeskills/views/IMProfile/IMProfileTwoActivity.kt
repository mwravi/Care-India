package com.careindia.lifeskills.views.IMProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.careindia.lifeskills.R
import com.careindia.lifeskills.ui.base.BaseActivity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.activities.MainActivity
import kotlinx.android.synthetic.main.activity_improfile_two.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileTwoActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_improfile_two)
        validate = Validate(this)
        tv_title.text = "IM Profile"


        initializeController()

    }

    override fun initializeController() {
        //apply click on view
        applyClickOnView()



        validate!!.fillSpinnerLanguage(
            this,
            spin_cate_picker_belong,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_cate_picker_belong)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_source_income,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_source_income)
        )



        validate!!.fillSpinnerLanguage(
            this,
            spin_type_emp,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_type_emp)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_sell_waste_collect,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_sell_waste_collect)
        )

        validate!!.fillSpinnerLanguage(
            this,
            spin_acess_mob_data,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )

        validate!!.fillCheckBoxes(
            this,
            multiCheck_lang_read,
            resources.getStringArray(R.array.language)
        )
        validate!!.fillCheckBoxes(this, lang_write, resources.getStringArray(R.array.language))
        validate!!.fillCheckBoxes(
            this,
            prefer_comni_speaking,
            resources.getStringArray(R.array.language)
        )
        validate!!.fillCheckBoxes(
            this,
            lang_prefer_mobile_use,
            resources.getStringArray(R.array.language)
        )
        validate!!.fillCheckBoxes(this, multiCheck, resources.getStringArray(R.array.language))
    }


    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_next.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_next -> {
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}