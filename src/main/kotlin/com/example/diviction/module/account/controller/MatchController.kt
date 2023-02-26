package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.MatchDto
import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.service.MatchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/match")
class MatchController(@Autowired private val matchService: MatchService) {

    @PostMapping("/save")
    fun saveMatch(@RequestBody matchDto : MatchDto)
    {
        matchService.saveMatch(matchDto)
    }

    @GetMapping("/get/{id}")
    fun getMatchByMatchingId(@PathVariable id :Long) : MatchResponseDto
    {
        return matchService.getMatchByMatchingId(id)
    }

    @GetMapping("/duplication/{id}")
    fun getPatientDuplication(@PathVariable id : Long) : Boolean
    {
        return matchService.patientDuplication(id)
    }
}
