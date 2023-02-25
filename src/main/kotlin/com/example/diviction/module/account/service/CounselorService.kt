package com.example.diviction.module.account.service

import com.example.diviction.module.account.dto.CounselorDto
import com.example.diviction.module.account.entity.Counselor
import com.example.diviction.module.account.repository.CounselorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CounselorService(private val counselorRepository: CounselorRepository) {

    fun getCounselorByEmail(email : String) : CounselorDto
    {
        var cur = counselorRepository.findByEmail(email)

        if(cur.isPresent)
        {
            var counselor : Counselor = cur.get()

            return counselor.toDto()
        }

        else{
            throw RuntimeException("해당 이메일의 상담사는 존재하지 않습니다.")
        }
    }

    fun getCounselorById(id : Long) : CounselorDto
    {
        var cur = counselorRepository.findById(id)

        if(cur.isPresent)
        {
            var counselor : Counselor = cur.get()

            return counselor.toDto()
        }
        else{
            throw RuntimeException("해당 Id의 상담사는 존재하지 않습니다.")
        }
    }

    fun saveCounselor(counselorDto: CounselorDto)
    {
        if(counselorRepository.findByEmail(counselorDto.email).isPresent)
        {
            throw RuntimeException("해당 이메일의 상담사는 이미 존재합니다.")
        }

        var counselor : Counselor = Counselor(
            email = counselorDto.email,
            password = counselorDto.password,
            name = counselorDto.name,
            birth = counselorDto.birth,
            address = counselorDto.address,
            gender =  counselorDto.gender,
            profile_img_url =  counselorDto.profile_img_url
        )

        counselorRepository.save(counselor)
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

    fun Counselor.toDto() : CounselorDto = CounselorDto(
        email, password, name, birth, address, gender, profile_img_url, confirm
    )
}
