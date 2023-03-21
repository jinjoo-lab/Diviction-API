package com.example.diviction.module.account.service

import com.example.diviction.infra.gcp.GCP_URLs.COUNSELOR_BASIC_IMG_URL
import com.example.diviction.infra.gcp.GcpStorageService
import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.dto.ResponseCounselorDto
import com.example.diviction.module.account.entity.Counselor
import com.example.diviction.module.account.repository.CounselorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class CounselorService(
    private val counselorRepository: CounselorRepository,
    private val gcpStorageService: GcpStorageService
) {
    fun Counselor.toResponseDto() : ResponseCounselorDto = ResponseCounselorDto(id!!,email, password, name, birth, address, gender, profile_img_url, confirm)
    fun getCounselorByEmail(email : String) : ResponseCounselorDto
    {
        val cur = counselorRepository.findByEmail(email)

        if(cur.isPresent)
        {
            val counselor : Counselor = cur.get()

            return counselor.toResponseDto()
        }

        else{
            throw RuntimeException("해당 이메일의 상담사는 존재하지 않습니다.")
        }
    }

    fun getCounselorById(id : Long) : ResponseCounselorDto
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
        val cur = counselorRepository.findByEmail(email)

        if(cur.isPresent)
        {
            val counselor = cur.get()

            return counselor.confirm
        }
        return false
    }

    @Transactional
    fun setConfirmByEmail(email : String)
    {
        val cur = counselorRepository.findByEmail(email)

        if(cur.isPresent)
        {
            val counselor = cur.get()

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
        val cur = counselorRepository.findById(id)

        val re_list = ArrayList<MatchResponseDto>()
        if(cur.isPresent)
        {
            val counselor = cur.get()

            val list = counselor.matching_list

            list.forEach {
               re_list.add(MatchResponseDto(matchId = it.id, counselorId = it.counselor.id ,counselorEmail = it.counselor.email, patientId = it.patient.id, patientEmail = it.patient.email))
            }

            return re_list
        }
        else{
           return re_list
        }
    }

    fun getAllCounselor() : List<ResponseCounselorDto>
    {
        val counselor = counselorRepository.findAll()

        val list : MutableList<ResponseCounselorDto> = mutableListOf()

        counselor.forEach {
            list.add(it.toResponseDto())
        }

        return list
    }

    fun updateCounselorImg(
        counselorId : Long,
        multipartFile: MultipartFile?
    ){
        val counselor = counselorRepository.getById(counselorId)
        if (multipartFile == null) {
            counselor.profile_img_url = COUNSELOR_BASIC_IMG_URL
        } else {
            counselor.profile_img_url = gcpStorageService.uploadFileToGCS(multipartFile)
        }
    }
}
