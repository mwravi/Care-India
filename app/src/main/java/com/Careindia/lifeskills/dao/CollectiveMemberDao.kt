package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.CollectiveMemberEntity
import com.careindia.lifeskills.entity.HouseholdProfileEntity

@Dao
interface CollectiveMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(collectiveMemberEntity: CollectiveMemberEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMemberData(collectiveMemberEntity: List<CollectiveMemberEntity>?)

    @Query("DELETE from tblCollectiveMember")
    fun deleteAllData()

    @Query("DELETE from tblCollectiveMember where Col_GUID=:Col_GUID")
    fun deleteAllDataByCollProfile(Col_GUID: String)

    @Query("Select * from tblCollectiveMember where Col_GUID=:Col_GUID")
    fun getAllMemberData(Col_GUID: String): LiveData<List<CollectiveMemberEntity>>

    @Query("Select count(*) from tblCollectiveMember where Col_GUID=:Col_GUID")
    fun getAllData(Col_GUID: String): Int

    @Query("Select count(*) from tblCollectiveMember where Col_GUID=:Col_GUID and Gender=:male")
    fun getAllMemberDataGender(Col_GUID: String, male: Int): Int

    @Query("select count(*) from tblCollectiveMember where IsEdited=1")
    fun getCollMemunsyncCount(): String

    @Query("select count(*) from tblCollectiveMember")
    fun getCollMemtotalCount(): String

    @Query("update tblCollectiveMember set HHGUID=:hhid, Col_GUID=:collGuid, IndvGuid=:memberId, Name=:memberName, Gender=:membersex,Category=:Category, Age=:memberage, Position=:memberpos, Isbank=:memberacc, Contact=:contactNo, Aadhaar=:aadharNo, UpdatedBy=:updatedBy, UpdatedOn=:updatedOn,IsEdited=:IsEdited,Status=0 where GUID=:guid")
    fun updatecollectiveMember(
        guid: String,
        collGuid: String,
        hhid: String,
        memberId: String,
        memberName: String,
        membersex: Int,
        Category: Int,
        memberage: Int,
        memberpos: String,
        memberacc: Int,
        contactNo: String,
        aadharNo: String,
        updatedBy: Int,
        updatedOn: String,
        IsEdited: Int
    )

    @Query("select * from tblCollectiveMember where GUID=:guid")
    fun getCollectiveMemberdatabyGuid(guid: String): LiveData<List<CollectiveMemberEntity>>

    @Delete
    fun deletemember(collectiveMemberEntity: CollectiveMemberEntity)

    @Query("select MAX(substr(IndvGuid,11 , 15)) from tblCollectiveMember")
    fun getCommunityCount(): Int

    @Query("select count(*) from tblCollectiveMember where IndvGuid=:MemberID and Col_GUID=:collguid")
    fun getMemberID(MemberID: String, collguid: String): Int

    @Query("select Initials from tblCollective where Col_GUID=:guid")
    fun getCommunity(guid: String): String

    @Query("Select * from tblCollectiveMember where isEdited = 1")
    fun getAllMemberDataNew(): List<CollectiveMemberEntity>

    @Query("Update tblCollectiveMember set IsEdited=0")
    fun updateIsEdited()

    @Query("Select Name from tblCollectiveMember where Col_GUID=:Guid")
    fun getColPrfDatabyGuid(Guid: String): String

    @Query("Select * from tblProfileHH where ZoneCode=:ZoneCode and Panchayat_Ward=:WardCode")
    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileHH where Panchayat_Ward=:PanchayatCode")
    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity>

    @Query("Select * from tblCollectiveMember where Col_GUID=:collguid")
    fun getCollectiveDataByCollectiveGuid(collguid: String): List<CollectiveMemberEntity>

    @Query("Update tblCollectiveMember set Status=:status where GUID =:memguid")
    fun updateStatusData(status: Int, memguid: String)
}