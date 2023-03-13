package com.example.diviction.module.drugofmember.controller

import com.example.diviction.module.drugofmember.dto.DrugOfMemberResponse
import com.example.diviction.module.drugofmember.dto.DrugOfMemberSaveRequest
import com.example.diviction.module.drugofmember.dto.MemberOfDrugResponse
import com.example.diviction.module.drugofmember.entity.DrugOfMember
import com.example.diviction.module.drugofmember.service.DrugOfMemberService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/drugofmember")
class DrugOfMemberController (
        private val drugOfMemberService: DrugOfMemberService
        ){

    @Operation(description = "약물-회원 매칭 리스트 조회 (등록된 모든 약물-회원 매칭 리스트 조회)")
    @GetMapping("/list")
    fun getDrugOfMemberList() : List<DrugOfMember>
    {
        return drugOfMemberService.getAllList()
    }

    @Operation(description = "약물-회원 매칭 id로 약물-회원 매칭 정보 삭제")
    @GetMapping("/delete/{id}")
    fun deleteDrugOfMember(@PathVariable id : Long)
    {
        drugOfMemberService.deleteDrugOfMember(id)
    }

    @Operation(description = "약물-회원 매칭 정보 저장")
    @PostMapping("/save")
    fun saveDrugOfMember(
            @RequestBody drugOfMemberSaveRequest: DrugOfMemberSaveRequest
    ) {
        drugOfMemberService.saveDrugOfMember(drugOfMemberSaveRequest)
    }

    @Operation(description = "약물-회원 매칭 리스트 조회 (회원 id로 약물-회원 매칭 리스트 조회)")
    @GetMapping("/list/bymember/{memberId}")
    fun getDrugOfMemberByMemberId(@PathVariable memberId : Long) : DrugOfMemberResponse
    {
        return drugOfMemberService.getListByMemberId(memberId)
    }

    @Operation(description = "약물-회원 매칭 리스트 조회 (약물 id로 약물-회원 매칭 리스트 조회)")
    @GetMapping("/list/bydrug/{drugId}")
    fun getDrugOfMemberByDrugId(@PathVariable drugId : Long) : MemberOfDrugResponse
    {
        return drugOfMemberService.getListByDrugId(drugId)
    }

    @Operation(description = "약물-회원 매칭 정보 조회 (약물-회원 매칭 id로 약물-회원 매칭 단건 조회)")
    @GetMapping("/{id}")
    fun getDrugOfMemberById(@PathVariable id : Long) : DrugOfMember
    {
        return drugOfMemberService.getDrugOfMember(id)
    }

    @Operation(description = "약물-회원 매칭 정보 삭제 (약물-회원 매칭 id로 약물-회원 매칭 단건 삭제)")
    @DeleteMapping("/delete/{id}")
    fun deleteDrugOfMemberById(@PathVariable id : Long) {
        drugOfMemberService.deleteDrugOfMember(id)
    }
}