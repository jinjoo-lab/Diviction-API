package com.example.diviction.module.account.entity

import com.example.diviction.module.constant.Gender
import com.example.diviction.module.consulting.dto.ConsultResponseDto
import com.example.diviction.module.consulting.entity.Consulting
import com.example.diviction.module.diagnosis.entity.DiagnosisResult
import lombok.Getter
import lombok.ToString
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Getter
@ToString
class Member (
    @Email
    @NotBlank
    var email : String,
    @NotBlank
    var password : String,
    @NotBlank
    var name : String,
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var birth : String,
    @NotBlank
    var address : String,
    @Enumerated(EnumType.STRING)
    var gender : Gender,
    @NotBlank
    var profile_img_url : String
    ){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @OneToOne(mappedBy = "patient", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var matching : Matching? = null

    @OneToMany(mappedBy = "member", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var diagnosisList : MutableList<DiagnosisResult> = mutableListOf()

    @OneToMany(mappedBy = "consultPatient", targetEntity = Consulting::class, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var consultingList : MutableList<ConsultResponseDto> = mutableListOf()
}
