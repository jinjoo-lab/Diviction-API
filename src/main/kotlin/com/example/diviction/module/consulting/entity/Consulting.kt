package com.example.diviction.module.consulting.entity

import com.example.diviction.module.account.entity.Counselor
import com.example.diviction.module.account.entity.Member
import lombok.Getter
import org.springframework.format.annotation.DateTimeFormat
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Entity
@Getter
@Table(name ="consulting")
class Consulting(
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    val consultPatient : Member,

    @ManyToOne
    @JoinColumn(name = "counselor_id",nullable = false)
    val consultCounselor : Counselor,

    @field: NotBlank
    val content : String,

    @field: NotBlank
    @field: Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
    val date : String
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

}
