package com.example.diviction.module.consulting.controller

import com.example.diviction.module.consulting.dto.ConsultRequestDto
import com.example.diviction.module.consulting.service.ConsultingService
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
}
