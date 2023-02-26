package com.example.diviction.module.diagnosis.controller

import com.example.diviction.module.diagnosis.dto.DiagnosisResultDto
import com.example.diviction.module.diagnosis.service.DiagnosisService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/diagnosis")
class DiagnosisController(private val diagnosisService: DiagnosisService) {

    @PostMapping("/save")
    fun saveDiagnosisResult(@RequestBody diagnosisResultDto: DiagnosisResultDto)
    {
        diagnosisService.saveDiagnosisResult(diagnosisResultDto)
    }
}
