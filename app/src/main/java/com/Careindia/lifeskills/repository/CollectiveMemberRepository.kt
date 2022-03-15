package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.CollectiveMemberDao
import com.careindia.lifeskills.entity.CollectiveMemberEntity
import com.careindia.lifeskills.entity.HouseholdProfileEntity


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
        hhid:String,
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
        IsEdited:Int
    ) {
        collectiveMemberDao.updatecollectiveMember(
            guid,
            collGuid,
            hhid,
            memberId,
            memberName,
            membersex,
            Category,
            memberage,
            memberpos,
            memberacc,
            contactNo,
            aadharNo,
            updatedBy,
            updatedOn,
            IsEdited
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
    fun getMemberID(MemberID:String,collguid:String):Int{
        return collectiveMemberDao!!.getMemberID(MemberID,collguid)
    }

    fun getCommunity(guid:String):String{
        return collectiveMemberDao!!.getCommunity(guid)
    }

    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity> {
        return collectiveMemberDao.gethhProfileDataWard(ZoneCode, WardCode)
    }

    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity> {
        return collectiveMemberDao.gethhProfileDataPanchayat(PanchayatCode)
    }

}