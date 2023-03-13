package com.example.diviction.module.checklist.controller

import com.example.diviction.module.checklist.dto.InsertCheckList
import com.example.diviction.module.checklist.dto.ResultCheckList
import com.example.diviction.module.checklist.dto.SearchCheckList
import com.example.diviction.module.checklist.service.CheckListService
import com.example.diviction.module.constant.CheckListState
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
@Tag(name = "Checklist")
@RestController
@RequestMapping("/checklist")
class CheckListController(private val checkListService: CheckListService) {
    @Operation(description = "체크리스트 저장 , InsertCheckList를 body값으로 입력받음 , 날짜 형식 (yyyy-MM-dd)")
    @PostMapping("/save")
    fun saveCheckList(@RequestBody insertCheckList: InsertCheckList)
    {
        checkListService.saveCheckList(insertCheckList)
    }
    @Operation(description = "캘린더에 표기하기 위한 월별 체크리스트 정보를 반환(없으면 빈 리스트) , 사용 방법 : startDate = 해당 월의 시작 날짜 ex(2023-03-01) , endDate = 해당 월의 마지막 날짜 ex(2023-03-31) , start와 end 날짜 사이 해당 회원의 모든 체크리스트 정보 반환")
    @PostMapping("/get/month")
    fun getMonthCheckList(@RequestBody searchCheckList: SearchCheckList) : List<ResultCheckList>
    {
        return checkListService.getMonthCheckList(searchCheckList)
    }
    @Operation(description = "오늘 날짜를 기준으로 회원 id에 해당하는 체크리스트를 반환 , 없을 경우 빈 리스트")
    @GetMapping("/get/today/{patient_id}")
    fun getDayCheckList(@PathVariable patient_id : Long) : List<ResultCheckList>
    {
        return checkListService.getDayCheckList(patient_id)
    }

    @Operation(description = "checklist 의 id로 상태 변화 , 상태값은 enum (CheckListState)")
    @GetMapping("id/{Id}/state/{checkState}")
    fun changeState(@PathVariable Id : Long,@PathVariable checkState : CheckListState)
    {
        checkListService.changeState(Id,checkState)
    }
    @Operation(description = "삭제 : checklist id")
    @DeleteMapping("/delete/{id}")
    fun deleteCheckList(@PathVariable id : Long)
    {
        checkListService.deleteCheckList(id)
    }
}
