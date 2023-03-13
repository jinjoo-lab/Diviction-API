package com.example.diviction.module.dass.controller

import com.example.diviction.module.dass.dto.DassSaveRequest
import com.example.diviction.module.dass.service.DassService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/dass")
class DassController(
        private val dassService: DassService
) {
    @Operation(description = "Dass 자가 진단 정보 저장")
    @PostMapping("/save")
    fun saveDass(
            @RequestBody dassSaveRequest: DassSaveRequest
    ) {
        dassService.saveDass(dassSaveRequest)
    }

    @Operation(description = "특정 회원의 Dass 자가 진단 정보 다건 조회")
    @GetMapping("/list/member/{memberId}")
    fun getDassListByMemberId(
            @PathVariable memberId: Long
    ) = dassService.getDassByMemberId(memberId)

    @Operation(description = "특정 날짜의 Dass 자가 진단 정보 다건 조회")
    @GetMapping("/list/date/{date}")
    fun getDassListByDate(
            @PathVariable
            @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
            date: String
    ) = dassService.getDassByDate(date)

    @Operation(description = "특정 회원의 특정 날짜의 Dass 자가 진단 정보 다건 조회")
    @GetMapping("/list/member/{memberId}/date/{date}")
    fun getDassListByMemberIdAndDate(
            @PathVariable memberId: Long,
            @PathVariable
            @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
            date: String
    ) = dassService.getDassByMemberIdAndDate(memberId, date)

    @Operation(description = "DASS Id로 특정 Dass 자가 진단 정보 단건 삭제")
    @DeleteMapping("/delete/{id}")
    fun deleteDass(
            @PathVariable id: Long
    ) {
        dassService.deleteDass(id)
    }
}