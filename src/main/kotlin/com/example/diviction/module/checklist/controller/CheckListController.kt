package com.example.diviction.module.checklist.controller

import com.example.diviction.module.checklist.dto.InsertCheckList
import com.example.diviction.module.checklist.dto.SearchCheckList
import com.example.diviction.module.checklist.service.CheckListService
import com.example.diviction.module.constant.CheckListState
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/checklist")
class CheckListController(private val checkListService: CheckListService) {

    @PostMapping("/save")
    fun saveCheckList(@RequestBody insertCheckList: InsertCheckList)
    {
        checkListService.saveCheckList(insertCheckList)
    }

    @PostMapping("/get")
    fun getCheckList(@RequestHeader(name = "RT") header : String,@RequestBody searchCheckList: SearchCheckList) : List<String>
    {
        return checkListService.getCheckList(searchCheckList)
    }

    @GetMapping("id/{Id}/state/{checkState}")
    fun changeState(@PathVariable Id : Long,@PathVariable checkState : CheckListState)
    {
        checkListService.changeState(Id,checkState)
    }
}
