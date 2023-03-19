package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.dto.ResponseCounselorDto
import com.example.diviction.module.account.service.CounselorService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
@Tag(name = "Counselor" , description = "상담자 confirm 수정 및 조회")
@RestController
@RequestMapping("/counselor")
class CounselorController(private val counselorService: CounselorService) {
    @Operation(description = "상담자 id(Long)으로 조회")
    @GetMapping("/id/{counselorId}")
    fun getCounselorById(@PathVariable(name = "counselorId") id : Long) : ResponseCounselorDto
    {
        return counselorService.getCounselorById(id)
    }
    @Operation(description = "상담자 email로 조회")
    @GetMapping("/email/{email}")
    fun getCounselorByEmail(@PathVariable email : String) : ResponseCounselorDto
    {
        return counselorService.getCounselorByEmail(email)
    }
    @Operation(description = "email의 상담자의 confirm 상태 조회")
    @GetMapping("/get/confirm/{email}")
    fun getConfirmByEmail(@PathVariable email: String) : Boolean
    {
        return counselorService.getConfirm(email)
    }
    @Operation(description = "email의 상담자의 confirm 상태 true로 변환")
    @GetMapping("/set/confirm/{email}")
    fun setConfirmByEmail(@PathVariable email: String)
    {
        counselorService.setConfirmByEmail(email)
    }

    @Operation(description = "상담자 삭제")
    @GetMapping("/delete/{id}")
    fun deleteCounselor(@PathVariable id :Long)
    {
        counselorService.deleteCounselor(id)
    }
    @Operation(description = "id(Long)의 상담자의 현재 매칭 정보를 list로 제공 , 매칭 정보 없을 경우 빈 리스트")
    @GetMapping("match/list/{id}")
    fun getMatchListById(id :Long) : List<MatchResponseDto>
    {
        return counselorService.getMatchListById(id)
    }

    @Operation(description = "모든 상담자의 정보(리스트) 반환, 없을 경우 빈 리스트 , 실제 서비스 시 잠궈야 함")
    @GetMapping("/all")
    fun getAllCounselor() : List<ResponseCounselorDto>
    {
        return counselorService.getAllCounselor()
    }
}
