package com.example.diviction.module.account.service

import com.example.diviction.module.account.dto.CounselorDto
import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.dto.SignUpCounselorDto
import com.example.diviction.module.account.entity.Counselor
import com.example.diviction.module.account.repository.CounselorRepository
import com.fasterxml.jackson.databind.RuntimeJsonMappingException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CounselorService(private val counselorRepository: CounselorRepository) {
    fun Counselor.toResponseDto() : SignUpCounselorDto = SignUpCounselorDto(id!!,email, password, name, birth, address, gender, profile_img_url, confirm)
    fun getCounselorByEmail(email : String) : SignUpCounselorDto
    {
        var cur = counselorRepository.findByEmail(email)

        if(cur.isPresent)
        {
            var counselor : Counselor = cur.get()

            return counselor.toResponseDto()
        }

        else{
            throw RuntimeException("해당 이메일의 상담사는 존재하지 않습니다.")
        }
    }

    fun getCounselorById(id : Long) : SignUpCounselorDto
    {
        var cur = counselorRepository.findById(id)

        if(cur.isPresent)
        {
            var counselor : Counselor = cur.get()

            return counselor.toResponseDto()
        }
        else{
            throw RuntimeException("해당 Id의 상담사는 존재하지 않습니다.")
        }
    }
    fun getConfirm(email: String) : Boolean
    {
        var cur = counselorRepository.findByEmail(email)

        if(cur.isPresent)
        {
            var counselor = cur.get()

            if(counselor.confirm)
            {
                return true
            }
            else
            {
                return false
            }
        }
        return false
    }

    @Transactional
    fun setConfirmByEmail(email : String)
    {
        var cur = counselorRepository.findByEmail(email)

        if(cur.isPresent)
        {
            var counselor = cur.get()

            counselor.confirm = true

            counselorRepository.save(counselor)
        }
    }

    fun deleteCounselor(id : Long)
    {
        counselorRepository.deleteById(id)
    }

    fun getMatchListById(id : Long) : List<MatchResponseDto>
    {
        var cur = counselorRepository.findById(id)

        var re_list = ArrayList<MatchResponseDto>()
        if(cur.isPresent)
        {
            var counselor = cur.get()

            var list = counselor.matching_list

            list.forEach {
               re_list.add(MatchResponseDto(matchId = it.id, counselorId = it.counselor.id ,counselorEmail = it.counselor.email, patientId = it.patient.id, patientEmail = it.patient.email))
            }

            return re_list
        }
        else{
           return re_list
        }
    }

    fun Counselor.toDto() : CounselorDto = CounselorDto(
       email, password, name, birth, address, gender, profile_img_url, confirm
    )

    fun getAllCounselor() : List<SignUpCounselorDto>
    {
        var counselor = counselorRepository.findAll()

        var list : MutableList<SignUpCounselorDto> = mutableListOf()

        counselor.forEach {
            list.add(it.toResponseDto())
        }

        return list
    }
}
