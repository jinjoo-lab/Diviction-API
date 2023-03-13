package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.MatchDto
import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.service.MatchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
@Tag(name = "Match", description = "매칭 저장 , 조회")
@RestController
@RequestMapping("/match")
class MatchController(@Autowired private val matchService: MatchService) {
    @Operation(description = "매칭 저장 (중독자 id, 상담자 id) 필요 : 결과 값 : 200 ok 매칭 결과, error : 500")
    @PostMapping("/save")
    fun saveMatch(@RequestBody matchDto : MatchDto) : ResponseEntity<MatchResponseDto>
    {
        return ResponseEntity(matchService.saveMatch(matchDto), HttpStatus.OK)
    }
    @Operation(description = "매칭 id(pk)로 정보 조회")
    @GetMapping("/get/{id}")
    fun getMatchByMatchingId(@PathVariable id :Long) : MatchResponseDto
    {
        return matchService.getMatchByMatchingId(id)
    }
    @Operation(description = "해당 id의 회원의 매칭 정보가 있는지 확인 , 결과값 : Boolean")
    @GetMapping("/duplication/{id}")
    fun getPatientDuplication(@PathVariable id : Long) : Boolean
    {
        return matchService.patientDuplication(id)
    }
    @Operation(description = "모든 매칭 정보 반환")
    @GetMapping("/all")
    fun gelAllMatch() : List<MatchResponseDto>
    {
        return matchService.getAllMatch()
    }

    @Operation(description = "매칭 id로 매칭 정보 삭제 , 이 기능은 설게 당시 상담자에게만 open하도록")
    @DeleteMapping("/delete/{id}")
    fun deleteMatch(@PathVariable id : Long)
    {
        matchService.deleteMatch(id)
    }
}
