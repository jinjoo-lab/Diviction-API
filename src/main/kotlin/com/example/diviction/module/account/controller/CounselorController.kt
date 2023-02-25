package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.CounselorDto
import com.example.diviction.module.account.service.CounselorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/counselor")
class CounselorController(private val counselorService: CounselorService) {

    @GetMapping("/id/{counselorId}")
    fun getCounselorById(@PathVariable(name = "counselorId") id : Long) : CounselorDto
    {
        return counselorService.getCounselorById(id)
    }

    @GetMapping("/email/{email}")
    fun getCounselorByEmail(@PathVariable email : String) : CounselorDto
    {
        return counselorService.getCounselorByEmail(email)
    }

    @GetMapping("/get/confirm/{email}")
    fun getConfirmByEmail(@PathVariable email: String) : Boolean
    {
        return counselorService.getConfirm(email)
    }

    @GetMapping("/set/confirm/{email}")
    fun setConfirmByEmail(@PathVariable email: String)
    {
        counselorService.setConfirmByEmail(email)
    }

    @PostMapping("/save")
    fun saveCounselor(@RequestBody counselorDto : CounselorDto)
    {
        counselorService.saveCounselor(counselorDto)
    }
}
