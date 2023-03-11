package com.example.diviction.module.DAST.controller

import com.example.diviction.module.DAST.dto.SaveDastDto
import com.example.diviction.module.DAST.service.DastService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dast")
class DastController(
    private val dastService: DastService
) {
    @PostMapping("/save")
    fun saveDast(@RequestBody saveDastDto: SaveDastDto)
    {
        dastService.saveDast(saveDastDto)
    }
}
