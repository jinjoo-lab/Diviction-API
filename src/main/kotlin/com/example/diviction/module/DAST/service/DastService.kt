package com.example.diviction.module.DAST.service

import com.example.diviction.module.DAST.dto.GetEmailDastDto
import com.example.diviction.module.DAST.dto.ResultDastDto
import com.example.diviction.module.DAST.dto.SaveDastDto
import com.example.diviction.module.DAST.entity.Dast
import com.example.diviction.module.DAST.repository.DastRepository
import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.account.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class DastService(
    private val dastRepository: DastRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun saveDast(dastDto: SaveDastDto)
    {
        val drugList : String = dastDto.drug.joinToString(",")
        val member : Member? = memberRepository.getByEmail(dastDto.member_email)

        val dast : Dast = Dast(
            drug = drugList,
            date = LocalDate.now(),
            frequency = dastDto.frequency,
            injection = dastDto.injection,
            cure = dastDto.cure,
            question = dastDto.question,
            dastMember = member!!
        )
        member.dastLists.add(dast)

        dastRepository.save(dast)
    }

    fun getDast(getEmailDastDto: GetEmailDastDto) : ResultDastDto
    {
        val dastList =  memberRepository.getByEmail(getEmailDastDto.member_email)!!.dastLists
        var result : Dast? = null

        dastList.forEach {
            if(it.date.equals(getEmailDastDto.date))
            {
                result = it
            }
        }

        if(result==null)
            throw RuntimeException("검사 결과가 존재하지 안습니다.")

        return result!!.toDto()
    }

    fun getDastByMemberIdAndDate(memberId : Long,date : LocalDate) : List<ResultDastDto>
    {
        var list : MutableList<ResultDastDto> = mutableListOf()
        dastRepository.getAllByDastMemberAndDate(memberRepository.getById(memberId),date).forEach {
            list.add(it.toDto())
        }

        return list
    }
    fun getDastByMemberId(id : Long) : List<ResultDastDto>
    {
        var list : MutableList<ResultDastDto> = mutableListOf()
        dastRepository.getAllByDastMember(memberRepository.getById(id)).forEach {
            list.add(it.toDto())
        }

        return list
    }

    fun delelteDast(id : Long)
    {
        dastRepository.deleteById(id)
    }

    fun Dast.toDto() : ResultDastDto = ResultDastDto(
            id!!,drug, date, frequency, injection, cure, question
        )

}
