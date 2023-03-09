package com.example.diviction.module.drugofmember.service

import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.drug.entity.Drug
import com.example.diviction.module.drug.repository.DrugRepository
import com.example.diviction.module.drugofmember.dto.DrugOfMemberResponse
import com.example.diviction.module.drugofmember.dto.DrugOfMemberSaveRequest
import com.example.diviction.module.drugofmember.dto.MemberOfDrugResponse
import com.example.diviction.module.drugofmember.entity.DrugOfMember
import com.example.diviction.module.drugofmember.repository.DrugOfMemberRepository
import org.springframework.stereotype.Service

@Service
class DrugOfMemberService (
        private val drugOfMemberRepository: DrugOfMemberRepository,
        private val memberRepository: MemberRepository,
        private val drugRepository: DrugRepository
        ){
    fun getAllList() : List<DrugOfMember> {
        return drugOfMemberRepository.findAll()
    }

    fun getListByMemberId(memberId : Long) : DrugOfMemberResponse {
        val drugOfMemberList = drugOfMemberRepository.findByMemberId(memberId)
        val result = ArrayList<Drug>()
        for (drugOfMember in drugOfMemberList) {
            result.add(drugOfMember.drug!!)
        }
        return DrugOfMemberResponse(result)
    }

    fun getListByDrugId(drugId : Long) : MemberOfDrugResponse {
        val memberOfDrugList = drugOfMemberRepository.findByDrugId(drugId)
        val result = ArrayList<Member>()
        for (drugOfMember in memberOfDrugList) {
            result.add(drugOfMember.member!!)
        }
        return MemberOfDrugResponse(result)
    }

    fun deleteDrugOfMember(id : Long) {
        drugOfMemberRepository.deleteById(id)
    }

    fun saveDrugOfMember(drugOfMemberSaveRequest: DrugOfMemberSaveRequest) {
        if (checkDrugOfMemberDuplicate(drugOfMemberSaveRequest.memberId!!, drugOfMemberSaveRequest.drugId!!)) {
            throw RuntimeException("이미 존재하는 데이터입니다.")
        }
        val member = memberRepository.findById(drugOfMemberSaveRequest.memberId!!).get()
        val drug = drugRepository.findById(drugOfMemberSaveRequest.drugId!!).get()
        val drugOfmember = DrugOfMember(member, drug)
        drugOfMemberRepository.save(drugOfmember)
    }

    fun getDrugOfMember(id : Long) : DrugOfMember {
        val result = drugOfMemberRepository.findById(id)
        if (result.isPresent) {
            return result.get()
        }
        else {
            throw RuntimeException("존재하지 않는 데이터입니다.")
        }
    }

    fun checkDrugOfMemberDuplicate(memberId : Long, drugId : Long) : Boolean {
        return drugOfMemberRepository.findByMemberIdAndDrugId(memberId, drugId).isPresent
    }
}