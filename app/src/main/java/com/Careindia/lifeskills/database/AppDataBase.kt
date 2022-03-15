package com.careindia.lifeskills.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.careindia.lifeskills.dao.*
import com.careindia.lifeskills.entity.*

@Database(
    entities = [HouseholdProfileEntity::class, IndividualProfileEntity::class, CollectiveEntity::class, CollectiveMemberEntity::class
        ,MstStateEntity::class,MstDistrictEntity::class,MstZoneEntity::class,MstPanchayat_WardEntity::class,MstLocalityEntity::class,MstLookupEntity::class,MstUserEntity::class,PrimaryDataEntity::class,
        PsychometricEntity::class,CollectiveMeetingEntity::class,BeneficiaryEntity::class,BeneficiaryDetailEntity::class, CollectiveProgressTrackerEntity::class,AssessmentDetailEntity::class,AssessmentEntity::class,MstAssessmentEntity::class,
        TrainingEntity::class,ParticipantAttendanceDetailEntity::class,MstTrainerEntity::class,TrainingParticipantDetailEntity::class],
    version = 1
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun hhProfileDao(): HouseholdProfileDao
    abstract fun imProfileDao(): IndividualProfileDao
    abstract fun collectiveDao(): CollectiveDao
    abstract fun collectiveMemDao(): CollectiveMemberDao
    abstract fun mstStateDao(): MstStateDao
    abstract fun mstDistrictDao(): MstDistrictDao
    abstract fun mstZoneDao(): MstZoneDao
    abstract fun mstPanchayatWardDao(): MstPanchayatWardDao
    abstract fun mstLocalityDao(): MstLocalityDao
    abstract fun mstLookupDao(): MstLookupDao
    abstract fun mstUserDao(): MstUserDao
    abstract fun primaryDataDao(): PrimaryDataDao
    abstract fun psychometricDao(): PsychometricDao
    abstract fun collectiveMeetingDao(): CollectiveMeetingDao
    abstract fun collectiveProgressTrackerDao(): CollectiveProgressTrackerDao
    abstract fun assessmentDetailDao(): AssessmentDetailDao
    abstract fun assessmentDao(): AssessmentDao
    abstract fun mstAssessmentDao(): MstAssessmentDao
    abstract fun beneficiaryTrackerDetailDao(): BeneficiaryTrackerDetailDao
    abstract fun beneficiaryDao(): BeneficiaryProgressDao
    abstract fun trainingDao(): TrainingDao
    abstract fun participantAttendanceDetailDao(): ParticipantAttendanceDetailDao
    abstract fun mstTrainerDao(): MstTrainerDao
    abstract fun trainingParticipantDetailDao(): TrainingParticipantDetailDao




    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "CAREINDIA_DB.db"
                    ).build()
                }
                return instance
            }
        }
    }
}





