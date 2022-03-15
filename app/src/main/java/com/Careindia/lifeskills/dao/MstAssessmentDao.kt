package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstAssessmentEntity

@Dao
interface MstAssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithCondition(entity: List<MstAssessmentEntity>?)

    @Query("DELETE FROM mstAssessment")
    fun deleteAll()

    @Query("select * from mstAssessment where ModuleID=:ModuleID and PageID=:PageID order by SeqNo")
    fun getAllQuestion(ModuleID:Int,PageID:Int): List<MstAssessmentEntity>

 /*   @Query("Select distinct PageID from MstAssessment where ModuleID =:ModuleID  order by  SeqNo")
    fun getDistinctPage(ModuleID:Int): List<MstAssessmentEntity>*/

    @Query("SELECT  PageID,QID  from MstAssessment where ModuleID =:ModuleID GROUP BY PageID order by  SeqNo")
    fun getDistinctPage(ModuleID:Int): List<MstAssessmentEntity>

    @Query("SELECT  PageID,QID  from MstAssessment where FormID =:ModuleID  GROUP BY PageID order by  PageID")
    fun getDistinctPageBen(ModuleID:Int): List<MstAssessmentEntity>

    @Query("select PageHeader from mstAssessment where PageID=:PageID and FormID=:FormID order by SeqNo limit 1")
    fun getPageHeader(PageID: Int,FormID:Int): String


}