package com.example.diviction.module.drug.controller

import com.example.diviction.module.drug.dto.DrugRegistrationRequest
import com.example.diviction.module.drug.entity.Drug
import com.example.diviction.module.drug.service.DrugService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/drug")
class DrugController (
        private val drugService: DrugService
        ){

    @Operation(description = "약물 등록")
    @PostMapping("/save")
    fun saveDrug(@RequestBody drugRegistrationRequest : DrugRegistrationRequest)
    {
        drugService.saveDrug(drugRegistrationRequest)
    }

    @Operation(description = "약물 리스트 조회 (등록된 모든 약물 조회)")
    @GetMapping("/list")
    fun getDrugList() : List<Drug>
    {
        return drugService.getDrugList()
    }

    @Operation(description = "약물 id로 약물 정보 삭제")
    @GetMapping("/delete/{id}")
    fun deleteDrug(@PathVariable id : Long)
    {
        drugService.deleteDrug(id)
    }
}