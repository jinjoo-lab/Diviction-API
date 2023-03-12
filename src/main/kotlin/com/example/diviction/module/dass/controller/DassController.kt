package com.example.diviction.module.dass.controller

import com.example.diviction.module.dass.dto.DassSaveRequest
import com.example.diviction.module.dass.service.DassService
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/dass")
class DassController(
        private val dassService: DassService
) {
    @PostMapping("/save")
    fun saveDass(
            @RequestBody dassSaveRequest: DassSaveRequest
    ) {
        dassService.saveDass(dassSaveRequest)
    }

    @GetMapping("/list/member/{memberId}")
    fun getDassListByMemberId(
            @PathVariable memberId: Long
    ) = dassService.getDassByMemberId(memberId)

    @GetMapping("/list/date/{date}")
    fun getDassListByDate(
            @PathVariable
            @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
            date: String
    ) = dassService.getDassByDate(date)

    @GetMapping("/list/member/{memberId}/date/{date}")
    fun getDassListByMemberIdAndDate(
            @PathVariable memberId: Long,
            @PathVariable
            @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
            date: String
    ) = dassService.getDassByMemberIdAndDate(memberId, date)

    @DeleteMapping("/delete/{id}")
    fun deleteDass(
            @PathVariable id: Long
    ) {
        dassService.deleteDass(id)
    }
}