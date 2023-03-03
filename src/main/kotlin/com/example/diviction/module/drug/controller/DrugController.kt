package com.example.diviction.module.drug.controller

import com.example.diviction.module.drug.dto.DrugRegistrationRequest
import com.example.diviction.module.drug.entity.Drug
import com.example.diviction.module.drug.service.DrugService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/drug")
class DrugController (
        private val drugService: DrugService
        ){

    @PostMapping("/save")
    fun saveDrug(@RequestBody drugRegistrationRequest : DrugRegistrationRequest)
    {
        drugService.saveDrug(drugRegistrationRequest)
    }

    @GetMapping("/list")
    fun getDrugList() : List<Drug>
    {
        return drugService.getDrugList()
    }

    @GetMapping("/delete/{id}")
    fun deleteDrug(@PathVariable id : Long)
    {
        drugService.deleteDrug(id)
    }
}