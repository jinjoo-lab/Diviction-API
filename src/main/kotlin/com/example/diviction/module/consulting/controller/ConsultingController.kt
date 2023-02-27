package com.example.diviction.module.consulting.controller

import com.example.diviction.module.consulting.dto.ConsultRequestDto
import com.example.diviction.module.consulting.dto.ConsultResponseDto
import com.example.diviction.module.consulting.dto.ConsultUpdateDto
import com.example.diviction.module.consulting.entity.Consulting
import com.example.diviction.module.consulting.service.ConsultingService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/consult")
class ConsultingController(private val consultingService: ConsultingService) {
    @PostMapping("/save")
    fun saveConsultingLog(@RequestBody consultRequestDto: ConsultRequestDto)
    {
        consultingService.saveConsultingLog(consultRequestDto)
    }

    @GetMapping("/get/id/{patient_id}")
    fun getConsultingLogByPatientId(@PathVariable patient_id : Long) : List<ConsultResponseDto>
    {
        return consultingService.getConsultingLogByPatientId(patient_id)
    }

    @GetMapping("/get/email/{patient_email}")
    fun getConsultingLogByPatientEmail(@PathVariable patient_email : String) : List<ConsultResponseDto>
    {
        return consultingService.getConsultingLogByPatientEmail(patient_email)
    }

    @PostMapping("/update/{id}")
    fun updateConsultLog(@PathVariable id : Long, @RequestBody consultUpdateDto: ConsultUpdateDto){
        consultingService.updateConsultLog(id,consultUpdateDto)
    }
}
