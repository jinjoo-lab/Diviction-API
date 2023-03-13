package com.example.diviction.module.consulting.controller

import com.example.diviction.module.consulting.dto.ConsultRequestDto
import com.example.diviction.module.consulting.dto.ConsultResponseDto
import com.example.diviction.module.consulting.dto.ConsultUpdateDto
import com.example.diviction.module.consulting.entity.Consulting
import com.example.diviction.module.consulting.service.ConsultingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@Tag(name="Consult", description = "상담 정보 관리")
@RestController
@RequestMapping("/consult")
class ConsultingController(private val consultingService: ConsultingService) {
    @Operation(description = "상담 정보 저장")
    @PostMapping("/save")
    fun saveConsultingLog(@RequestBody consultRequestDto: ConsultRequestDto)
    {
        consultingService.saveConsultingLog(consultRequestDto)
    }
    @Operation(description = "중독자 Id 로 배정된 상담 기록들을 날짜의 최신순으로 전달")
    @GetMapping("/get/id/{patient_id}")
    fun getConsultingLogByPatientId(@PathVariable patient_id : Long) : List<ConsultResponseDto>
    {
        return consultingService.getConsultingLogByPatientId(patient_id)
    }
    @Operation(description = "중독자 email 로 배정된 상담 기록들을 날짜의 최신순으로 전달")
    @GetMapping("/get/email/{patient_email}")
    fun getConsultingLogByPatientEmail(@PathVariable patient_email : String) : List<ConsultResponseDto>
    {
        return consultingService.getConsultingLogByPatientEmail(patient_email)
    }
    @Operation(description = "중독자의 상담 정보 변경")
    @PostMapping("/update/{id}")
    fun updateConsultLog(@PathVariable id : Long, @RequestBody consultUpdateDto: ConsultUpdateDto){
        consultingService.updateConsultLog(id,consultUpdateDto)
    }
}
