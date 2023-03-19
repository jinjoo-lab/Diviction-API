package com.example.diviction.module.DAST.service

import com.example.diviction.module.DAST.dto.GetDastDto
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

    fun getDast(getDastDto: GetDastDto) : ResultDastDto
    {
        val dastList =  memberRepository.getByEmail(getDastDto.member_email)!!.dastLists
        var result : Dast? = null

        dastList.forEach {
            if(it.date.equals(getDastDto.date))
            {
                result = it
            }
        }

        if(result==null)
            throw RuntimeException("검사 결과가 존재하지 안습니다.")

        return result!!.toDto()
    }

    fun Dast.toDto() : ResultDastDto = ResultDastDto(
            drug, date, frequency, injection, cure, question
        )

}
