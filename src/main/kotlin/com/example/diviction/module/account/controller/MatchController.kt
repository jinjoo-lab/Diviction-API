package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.MatchDto
import com.example.diviction.module.account.service.MatchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/match")
class MatchController(@Autowired private val matchService: MatchService) {

    @PostMapping("/save")
    fun saveMatch(@RequestBody matchDto : MatchDto)
    {
        matchService.saveMatch(matchDto)
    }
}
