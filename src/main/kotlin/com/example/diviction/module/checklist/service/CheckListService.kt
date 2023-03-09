package com.example.diviction.module.checklist.service

import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.checklist.dto.InsertCheckList
import com.example.diviction.module.checklist.dto.SearchCheckList
import com.example.diviction.module.checklist.entity.CheckList
import com.example.diviction.module.checklist.repository.CheckListRepository
import com.example.diviction.module.constant.CheckListState
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Service
class CheckListService(
    private val checkListRepository: CheckListRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun saveCheckList(insertCheckList: InsertCheckList)
    {
        val member = memberRepository.getById(insertCheckList.patientId)
        var checkList = CheckList(
            checkPatient = member,
            startDate =  insertCheckList.startDate,
            endDate = insertCheckList.endDate,
            content = insertCheckList.content
        )
        member.checkLists.add(checkList)
        checkListRepository.save(checkList)
    }

    @Transactional
    fun getCheckList(searchCheckList: SearchCheckList) : List<String>
    {
        val member = memberRepository.getById(searchCheckList.patientId)
        var list = checkListRepository.findAllByCheckPatientAndStartDateAndEndDate(member,searchCheckList.startDate,searchCheckList.endDate)

        var returnList = ArrayList<String>()

        list.forEach {
            returnList.add(it.content)
        }

        return returnList
    }

    @Transactional
    fun changeState(Id : Long,checkState : CheckListState)
    {
        var checkList  = checkListRepository.getById(Id)
        checkList.state = checkState
    }
}
