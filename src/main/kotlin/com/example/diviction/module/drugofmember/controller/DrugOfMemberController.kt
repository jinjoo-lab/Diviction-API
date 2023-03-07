package com.example.diviction.module.drugofmember.controller

import com.example.diviction.module.drugofmember.dto.DrugOfMemberResponse
import com.example.diviction.module.drugofmember.dto.DrugOfMemberSaveRequest
import com.example.diviction.module.drugofmember.dto.MemberOfDrugResponse
import com.example.diviction.module.drugofmember.entity.DrugOfMember
import com.example.diviction.module.drugofmember.service.DrugOfMemberService
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

    @GetMapping("/list")
    fun getDrugOfMemberList() : List<DrugOfMember>
    {
        return drugOfMemberService.getAllList()
    }

    @GetMapping("/delete/{id}")
    fun deleteDrugOfMember(@PathVariable id : Long)
    {
        drugOfMemberService.deleteDrugOfMember(id)
    }

    @PostMapping("/save")
    fun saveDrugOfMember(
            @RequestBody drugOfMemberSaveRequest: DrugOfMemberSaveRequest
    ) {
        drugOfMemberService.saveDrugOfMember(drugOfMemberSaveRequest)
    }

    @GetMapping("/get/bymember/{memberId}")
    fun getDrugOfMemberByMemberId(@PathVariable memberId : Long) : DrugOfMemberResponse
    {
        return drugOfMemberService.getListByMemberId(memberId)
    }

    @GetMapping("/get/bydrug/{drugId}")
    fun getDrugOfMemberByDrugId(@PathVariable drugId : Long) : MemberOfDrugResponse
    {
        return drugOfMemberService.getListByDrugId(drugId)
    }

    @GetMapping("/get/{id}")
    fun getDrugOfMemberById(@PathVariable id : Long) : DrugOfMember
    {
        return drugOfMemberService.getDrugOfMember(id)
    }
}