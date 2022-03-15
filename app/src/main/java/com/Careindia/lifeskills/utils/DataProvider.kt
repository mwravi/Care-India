package com.mw.survey.views.activities

import android.annotation.SuppressLint
import android.database.Cursor
import android.util.Log
import androidx.sqlite.db.SupportSQLiteDatabase
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.model.MstFormQuestionModel
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate

class DataProvider {
    var dbSurvey: SupportSQLiteDatabase? = null
    private var cursor: Cursor? = null
    private var cursor1: Cursor? = null
    private var cursorgetRecord: Cursor? = null
    var skipValue = ""
    fun executeSql(Sql: String?) {
        try {
            if (dbSurvey == null) {
                dbSurvey =
                    CareIndiaApplication().getDataBaseObj()!!.getOpenHelper().getWritableDatabase()
            }
            dbSurvey!!.execSQL(Sql)
        } catch (exception: Exception) {
            Log.e(
                "DataProvider",
                "Error in executeSql :: " + exception.message
            )
        }
    }


    @SuppressLint("Range")
    fun getMSTFormQuestion(
        ModuleID: Int,
        LanguageID: Int,
        iPageNo: Int,
        validate: Validate
    ): ArrayList<MstFormQuestionModel?>? {
        var data: ArrayList<MstFormQuestionModel?>? = null
        var sql = ""
        if (LanguageID == 1) {

            sql =
                "select * from mstAssessment where FormID=$ModuleID and PageID=$iPageNo order by SeqNo"
        }
        cursor = null
        try {
            if (dbSurvey == null) {
                dbSurvey =
                    CareIndiaApplication().getDataBaseObj()!!.getOpenHelper().getWritableDatabase()
            }
            cursor = dbSurvey!!.query(sql, null)
            if (cursor != null) {
                data = ArrayList()
                cursor!!.moveToFirst()
                while (!cursor!!.isAfterLast) {
                    val form = MstFormQuestionModel()
                    form.QID = (cursor!!.getInt(cursor!!.getColumnIndex("QID")))
                    form.ModuleID = (cursor!!.getInt(cursor!!.getColumnIndex("ModuleID")))
                    form.Question = (
                            returnStringValue(
                                cursor!!.getString(
                                    cursor!!.getColumnIndex(
                                        "Question"
                                    )
                                )
                            )
                            )
                    form.SeqNo = (cursor!!.getInt(cursor!!.getColumnIndex("SeqNo")))
                    form.Question = (
                            returnStringValue(
                                cursor!!.getString(
                                    cursor!!.getColumnIndex(
                                        "Question"
                                    )
                                )
                            )
                            )

                    form.LookupID = (cursor!!.getInt(cursor!!.getColumnIndex("LookupID")))
                    form.IsActive = (cursor!!.getInt(cursor!!.getColumnIndex("IsActive")))
                    form.CreatedBy = (cursor!!.getInt(cursor!!.getColumnIndex("CreatedBy")))
                    form.Createdon = (
                            returnStringValue(
                                cursor!!.getString(
                                    cursor!!.getColumnIndex(
                                        "Createdon"
                                    )
                                )
                            )
                            )

                    form.Updatedby = (cursor!!.getInt(cursor!!.getColumnIndex("Updatedby")))
                    form.UpdatedOn = (
                            returnStringValue(
                                cursor!!.getString(
                                    cursor!!.getColumnIndex(
                                        "UpdatedOn"
                                    )
                                )
                            )
                            )
                    form.PageID = (cursor!!.getInt(cursor!!.getColumnIndex("PageID")))

                    form.PageHeader = (
                            returnStringValue(
                                cursor!!.getString(
                                    cursor!!.getColumnIndex(
                                        "PageHeader"
                                    )
                                )
                            )
                            )

                    form.FormID = (cursor!!.getInt(cursor!!.getColumnIndex("FormID")))
                    form.QuestionType = (cursor!!.getInt(cursor!!.getColumnIndex("QuestionType")))
                    form.QNo =
                        (returnStringValue(cursor!!.getString(cursor!!.getColumnIndex("QNo"))))
                    form.DependentQuestion = (
                            returnStringValue(
                                cursor!!.getString(
                                    cursor!!.getColumnIndex(
                                        "DependentQuestion"
                                    )
                                )
                            )
                            )

                    form.DependentOption = (
                            returnStringValue(
                                cursor!!.getString(
                                    cursor!!.getColumnIndex(
                                        "DependentOption"
                                    )
                                )
                            )
                            )



                    form.Skip = (
                            returnStringValue(
                                cursor!!.getString(
                                    cursor!!.getColumnIndex(
                                        "Skip"
                                    )
                                )
                            )
                            )

                    form.Link = (
                            returnStringValue(
                                cursor!!.getString(
                                    cursor!!.getColumnIndex(
                                        "Link"
                                    )
                                )
                            )
                            )
                    if ((form.Skip!!.isNotEmpty() || form.Link!!.isNotEmpty()) && skipQuestion(form.Skip!!, validate, form.Link!!) == 1
                    ) {
                        val sqlDelete =
                            "Delete from tblBeneficiaryDetail where Bene_GUID='" + validate.RetriveSharepreferenceString(
                                AppSP.Ben_GUID
                            )
                                .toString() + "' and QID=" + form.QID
                                .toString() + ""
                        executeSql(sqlDelete)

                    } else {
                        data.add(form)
                    }

                    //data.add(form)


                    cursor!!.moveToNext()
                }
                cursor!!.close()
            }
            return data
        } catch (exception: Exception) {
            Log.e(
                "DataProvider",
                "Error in MSTFormQuestion :: " + exception.message
            )
        }
        return data
    }


    fun returnStringValue(myString: String?): String {
        var iValue = ""
        if (myString != null && !myString.equals(
                "null",
                ignoreCase = true
            ) && myString.length > 0
        ) {
            iValue = myString
        }
        return iValue

    }


    fun skipQuestion(condition: String, validate: Validate,Link:String): Int {
        var iValue = 0
        var breakLoop=0
        val sql =
            "Select count(*) from tblBeneficiaryDetail  where  Bene_GUID='" + validate.RetriveSharepreferenceString(
                AppSP.Ben_GUID
            ) + "' and  " + condition
        if (getMaxRecord(sql) > 0) {
            iValue = 1
        }

        if (Link.isNotEmpty()) {
            val opt: Array<String> = Link.split(":".toRegex()).toTypedArray()
            val sql =
                "Select QuestionValue from tblBeneficiaryDetail  where  Bene_GUID='" + validate.RetriveSharepreferenceString(
                    AppSP.Ben_GUID
                ) + "' and  QID=" + opt[0] + ""
            val response_array: Array<String> =
                getRecord(sql)?.split(",".toRegex())!!.toTypedArray()
            val logic_array: Array<String> = opt[1].split(",".toRegex()).toTypedArray()
            for (j in response_array.indices) {
                if (breakLoop==99)
                {
                    break
                }
                for (i in logic_array.indices) {
                    if (response_array[j].equals(logic_array[i], ignoreCase = true)) {
                        iValue=0
                        breakLoop=99
                        break
                    } else
                    {
                        iValue=1
                    }
                }
            }


        }


        return iValue
    }


    fun getMaxRecord(Sql: String?): Int {
        var iIntegerValue = 0
        try {
            cursor1 = null
            if (dbSurvey == null) {
                dbSurvey =
                    CareIndiaApplication().getDataBaseObj()!!.getOpenHelper().getWritableDatabase()
            }
            cursor1 = dbSurvey!!.query(Sql, null)
            if (cursor1 != null) {
                cursor1!!.moveToFirst()
                while (cursor1!!.isAfterLast == false) {
                    iIntegerValue = cursor1!!.getInt(0)
                    cursor1!!.moveToNext()
                }
                cursor1!!.close()
            }
        } catch (exception: java.lang.Exception) {
            Log.e(
                "DataProvider",
                "Error in getMaxRecord :: " + exception.message
            )
        }
        return iIntegerValue
    }


    @SuppressLint("Range")
    fun getDynamicVal(sql: String?): java.util.ArrayList<HashMap<String?, String?>?>? {
        var data: java.util.ArrayList<HashMap<String?, String?>?>? = null
        cursor = null
        try {
            if (dbSurvey == null) {
                dbSurvey = CareIndiaApplication().getDataBaseObj()!!.openHelper.writableDatabase
            }
            cursor = dbSurvey?.query(sql, null)
            if (cursor != null) {
                data = java.util.ArrayList()
                cursor!!.moveToFirst()
                while (!cursor!!.isAfterLast) {
                    val params = HashMap<String?, String?>()
                    for (i in cursor!!.columnNames.indices) {
                        params[cursor!!.getColumnName(i)] = returnStringValue(
                            cursor!!.getString(
                                cursor!!.getColumnIndex(
                                    cursor!!.getColumnName(i)
                                )
                            )
                        )
                    }

                    data.add(params)
                    cursor!!.moveToNext()
                }
                cursor!!.close()
            }
            return data
        } catch (exception: java.lang.Exception) {
            Log.e(
                "DataProvider",
                "Error in MSTFormQuestion :: " + exception.message
            )
        }
        return data
    }


    fun getRecord(Sql: String?): String? {
        var sStringValue: String? = ""
        try {
            cursorgetRecord = null
            if (dbSurvey == null) {
                dbSurvey = CareIndiaApplication().getDataBaseObj()!!.openHelper.writableDatabase
            }
            cursorgetRecord = dbSurvey!!.query(Sql, null)
            if (cursorgetRecord != null) {
                cursorgetRecord!!.moveToFirst()
                while (cursorgetRecord!!.isAfterLast == false) {
                    sStringValue = cursorgetRecord!!.getString(0)
                    cursorgetRecord!!.moveToNext()
                }
                cursorgetRecord!!.close()
            }
        } catch (exception: java.lang.Exception) {
            Log.e(
                "DataProvider",
                "Error in getRecord :: " + exception.message
            )
        }
        return sStringValue
    }

}
