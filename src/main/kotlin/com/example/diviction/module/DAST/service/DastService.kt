package com.example.diviction.module.DAST.service

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
        val member : Member? = memberRepository.getByEmail(dastDto.userId)

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
}
