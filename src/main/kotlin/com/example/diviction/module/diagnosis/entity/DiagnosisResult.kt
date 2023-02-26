package com.example.diviction.module.diagnosis.entity

import com.example.diviction.module.account.entity.Member
import lombok.Getter

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
    @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
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
