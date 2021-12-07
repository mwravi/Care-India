package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.CollectiveMemberDao
import com.careindia.lifeskills.entity.CollectiveMemberEntity


class CollectiveMemberRepository(private val collectiveMemberDao: CollectiveMemberDao) {

    fun insert(collectiveMemberEntity: CollectiveMemberEntity) {
        collectiveMemberDao.insertAllData(collectiveMemberEntity)
    }

    fun getAllMemberData(Col_GUID:String): LiveData<List<CollectiveMemberEntity>> {
        return collectiveMemberDao.getAllMemberData(Col_GUID)
    }

    fun update(
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
    ) {
        collectiveMemberDao.updatecollectiveMember(
            guid,
            collGuid,
            memberId,
            memberName,
            membersex,
            memberage,
            memberpos,
            memberacc,
            contactNo,
            aadharNo,
            updatedBy,
            updatedOn
        )
    }

    fun getCollectiveMemberdatabyGuid(guid: String): LiveData<List<CollectiveMemberEntity>> {
        return collectiveMemberDao.getCollectiveMemberdatabyGuid(guid)
    }

    fun deletemember(collectiveMemberEntity: CollectiveMemberEntity) {
        return collectiveMemberDao.deletemember(collectiveMemberEntity)
    }
    fun getCommunityCount():Int{
        return collectiveMemberDao!!.getCommunityCount()
    }
}