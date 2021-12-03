package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.CollectiveDao
import com.careindia.lifeskills.dao.CollectiveMemberDao
import com.careindia.lifeskills.dao.MstCommonDao
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveMemberEntity
import com.careindia.lifeskills.entity.MstCommonEntity


class CollectiveMemberRepository(private val collectiveMemberDao: CollectiveMemberDao, private val mstCommonDao: MstCommonDao) {

    fun insert(collectiveMemberEntity: CollectiveMemberEntity) {
     collectiveMemberDao.insertAllData(collectiveMemberEntity)
    }

    fun getmstCommonData(flag:Int):List<MstCommonEntity> {
        return mstCommonDao.getMstCommon(flag)
    }

    fun update(
        guid: String,
        collGuid: String,
        memberId: String,
        memberName: String,
        membersex: Int,
        memberage: Int,
        memberpos: Int,
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

    fun getCollectiveMemberdatabyGuid(guid:String): LiveData<List<CollectiveMemberEntity>> {
        return collectiveMemberDao.getCollectiveMemberdatabyGuid(guid)
    }


}