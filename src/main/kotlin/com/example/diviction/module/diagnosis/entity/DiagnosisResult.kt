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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val member : Member,

    @field: NotBlank
    @field: Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
    val date : String,

    @field: NotBlank
    val vP1 : Long,
    @field: NotBlank
    val vP2 : Long,
    @field: NotBlank
    val vP3 : Long
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

}
