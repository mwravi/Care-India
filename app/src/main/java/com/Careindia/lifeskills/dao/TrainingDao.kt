package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.TrainingEntity
import com.careindia.lifeskills.model.PrePostModel


@Dao
interface TrainingDao {

    @Query("DELETE from tblTraining")
    fun deleteTraining()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTrainingData(trainingEntity: List<TrainingEntity>?)

    @Query("Select * from tblTraining")
    fun getTrainingData(): LiveData<List<TrainingEntity>>

    @Query("Select * from tblTraining where TrainingID=:trainingID")
    fun getTrainingDatabyTrainingID(trainingID: Int): LiveData<List<TrainingEntity>>

    @Query("Select a.* from tblProfileIndividual a inner join tblTrainingParticipantDetail b on  a.IndGUID = b.IndGUID where b.TrainingID =:participantID")
    fun getTrainingIndvData(participantID: Int): List<IndividualProfileEntity>

    @Query("Select * from tblTraining a left join (select trainingid,count(case when gender=1 then 1 end)Male,count(case when gender=2 then 1 end)Female,count(case when gender=3 then 1 end)Transgender from tblTrainingParticipantDetail p inner join tblProfileIndividual i on p.IndGUID=i.indguid group by trainingid) b on a.TrainingID=b.TrainingID where a.TrainingID=:trainingID")
    fun getTrainingParticipantData(trainingID: Int): List<PrePostModel>

    @Query("Select * from tblTraining where DateoftrainingFrom >=:datefrom and DateoftrainingTo <=:dateto")
    fun getTrainingFilterData(datefrom: Long, dateto: Long): LiveData<List<TrainingEntity>>

}