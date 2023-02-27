package com.example.diviction.module.diagnosis.entity

import com.example.diviction.module.account.entity.Member
import lombok.Getter
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Entity
@Getter
@Table(name = "diagnosis_result")
class DiagnosisResult(
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    val member : Member,

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val date : String,

    @NotBlank
    val vP1 : Long,
    @NotBlank
    val vP2 : Long,
    @NotBlank
    val vP3 : Long
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

}
