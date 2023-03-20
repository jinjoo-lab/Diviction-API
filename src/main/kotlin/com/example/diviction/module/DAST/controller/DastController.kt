package com.example.diviction.module.DAST.controller

import com.example.diviction.module.DAST.dto.GetEmailDastDto
import com.example.diviction.module.DAST.dto.ResultDastDto
import com.example.diviction.module.DAST.dto.SaveDastDto
import com.example.diviction.module.DAST.service.DastService
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.Date
import javax.validation.constraints.Pattern

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
    @PostMapping("/get/info")
    fun getDast(@RequestBody getEmailDastDto: GetEmailDastDto) : ResultDastDto
    {
        return dastService.getDast(getEmailDastDto)
    }

    @Operation(description = "중독자 ID로 dast list 반환 , 없을경우 빈 리스트")
    @GetMapping("/list/member/{memberId}")
    fun getMemberDastList(@PathVariable memberId : Long) : List<ResultDastDto>
    {
        return dastService.getDastByMemberId(memberId)
    }

    @GetMapping("/list/memberId/{memberId}/date/{date}")
    fun getDastListByMemberIdAndDate(
        @PathVariable memberId: Long,
        @PathVariable
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        date : LocalDate
    ) : List<ResultDastDto>
    {
        return dastService.getDastByMemberIdAndDate(memberId,date)
    }

    @DeleteMapping("/delete/{dastId}")
    fun delelteDastById(@PathVariable dastId : Long)
    {
        dastService.delelteDast(dastId)
    }
}
