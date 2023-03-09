package com.example.diviction.module.drug.service

import com.example.diviction.module.drug.dto.DrugRegistrationRequest
import com.example.diviction.module.drug.entity.Drug
import com.example.diviction.module.drug.repository.DrugRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class DrugService (
        private val drugRepository: DrugRepository
        ){

    fun saveDrug(drugRegistrationRequest: DrugRegistrationRequest) {
        val drug = Drug(drugRegistrationRequest.name)
        if (checkDrugDuplicate(drug.name)) {
            throw IllegalArgumentException("이미 존재하는 약입니다.")
        }
        else {
            drugRepository.save(drug)
        }
    }

    fun getDrugList(): List<Drug> {
        return drugRepository.findAll()
    }

    fun getDrug(id: Long): Drug {
        return drugRepository.findById(id).get()
    }

    fun checkDrugDuplicate(name: String): Boolean {
        return drugRepository.findByName(name).isPresent
    }

    fun deleteDrug(id: Long)
    {
        drugRepository.deleteById(id)
    }
}