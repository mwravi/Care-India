package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.CollectiveMemberEntity

@Dao
interface CollectiveMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(collectiveMemberEntity: CollectiveMemberEntity)

    @Query("DELETE from tblCollectiveMember")
    fun deleteAllData()

    @Query("Select * from tblCollectiveMember where Col_GUID=:Col_GUID")
    fun getAllMemberData(Col_GUID:String): LiveData<List<CollectiveMemberEntity>>

    @Query("update tblCollectiveMember set Col_GUID=:collGuid, MemberID=:memberId, Name=:memberName, Gender=:membersex, Age=:memberage, Position=:memberpos, Isbank=:memberacc, Contact=:contactNo, Aadhaar=:aadharNo, UpdatedBy=:updatedBy, UpdatedOn=:updatedOn where GUID=:guid")
    fun updatecollectiveMember(
        guid: String,
        collGuid: String,
        memberId: String,
        memberName: String,
        membersex: Int,
        memberage: Int,
        memberpos: String,
        memberacc: Int,
        contactNo: String,
        aadharNo: String,
        updatedBy: Int,
        updatedOn: String
    )

    @Query("select * from tblCollectiveMember where GUID=:guid")
    fun getCollectiveMemberdatabyGuid(guid: String): LiveData<List<CollectiveMemberEntity>>

    @Delete
    fun deletemember(collectiveMemberEntity: CollectiveMemberEntity)

    @Query("Select count(MemberID) from tblCollectiveMember")
    fun getCommunityCount():Int
}