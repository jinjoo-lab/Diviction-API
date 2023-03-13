package com.example.diviction.module.DAST.controller

import com.example.diviction.module.DAST.dto.GetDastDto
import com.example.diviction.module.DAST.dto.ResultDastDto
import com.example.diviction.module.DAST.dto.SaveDastDto
import com.example.diviction.module.DAST.service.DastService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@Tag(name = "DAST", description = "DAST 검사 결과 저장")
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
    @Operation(description = "중독자의 email과 원하는 일자를 (yyyy-MM-dd)로 검색 , 결과가 없을 경우 500 error")
    @PostMapping("/get")
    fun getDast(@RequestBody getDastDto: GetDastDto) : ResultDastDto
    {
        return dastService.getDast(getDastDto)
    }

}
