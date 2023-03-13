package com.example.diviction.module.checklist.service

import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.checklist.dto.InsertCheckList
import com.example.diviction.module.checklist.dto.ResultCheckList
import com.example.diviction.module.checklist.dto.SearchCheckList
import com.example.diviction.module.checklist.entity.CheckList
import com.example.diviction.module.checklist.repository.CheckListRepository
import com.example.diviction.module.constant.CheckListState
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDate

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
    fun getMonthCheckList(searchCheckList: SearchCheckList) : List<ResultCheckList>
    {
        val member = memberRepository.getById(searchCheckList.patientId)

        var list = checkListRepository.findAllByCheckPatientAndStartDateAndEndDate(member,searchCheckList.startDate,searchCheckList.endDate)
        var result : MutableList<ResultCheckList> = mutableListOf()

        list.forEach {
            result.add(it.toResultDto())
        }

        return result
    }

    @Transactional
    fun getDayCheckList(id : Long) : List<ResultCheckList>
    {
        var member = memberRepository.getById(id)

        val list = checkListRepository.findAllByCheckPatientAndStartDate(member, LocalDate.now())

        val result : MutableList<ResultCheckList> = mutableListOf()

        list.forEach {
            result.add(it.toResultDto())
        }

        return result
    }

    @Transactional
    fun changeState(Id : Long,checkState : CheckListState)
    {
        var checkList  = checkListRepository.getById(Id)
        checkList.state = checkState
    }

    fun CheckList.toResultDto() : ResultCheckList = ResultCheckList(
        checkListId = id,
        patientId = checkPatient.id,
        startDate = startDate,
        endDate = endDate,
        content = content,
        state = state
    )

    fun deleteCheckList(id : Long)
    {
        checkListRepository.deleteById(id)
    }
}
