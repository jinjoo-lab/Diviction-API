package com.example.diviction.module.drugofmember.service

import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.drug.entity.Drug
import com.example.diviction.module.drugofmember.entity.DrugOfMember
import com.example.diviction.module.drugofmember.repository.DrugOfMemberRepository
import org.springframework.stereotype.Service

@Service
class DrugOfMemberService (
        private val drugOfMemberRepository: DrugOfMemberRepository
        ){
    fun getAllList() : List<DrugOfMember> {
        return drugOfMemberRepository.findAll()
    }

    fun getListByMemberId(memberId : Long) : List<DrugOfMember> {
        return drugOfMemberRepository.findByMemberId(memberId)
    }

    fun getListByDrugId(drugId : Long) : List<DrugOfMember> {
        return drugOfMemberRepository.findByDrugId(drugId)
    }

    fun deleteDrugOfMember(id : Long) {
        drugOfMemberRepository.deleteById(id)
    }

    fun saveDrugOfMember(member: Member, drug: Drug) {
        if (checkDrugOfMemberDuplicate(member.id!!, drug.id!!)) {
            throw RuntimeException("이미 존재하는 데이터입니다.")
        }

        var drugOfmember = DrugOfMember()
        drugOfmember.member = member
        drugOfmember.drug = drug
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