package com.example.diviction.module.account.entity

import com.example.diviction.module.DAST.entity.Dast
import com.example.diviction.module.checklist.entity.CheckList
import com.example.diviction.module.constant.Gender
import com.example.diviction.module.consulting.dto.ConsultResponseDto
import com.example.diviction.module.consulting.entity.Consulting
import com.example.diviction.module.diagnosis.entity.DiagnosisResult
import com.example.diviction.security.constants.Authority
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Getter
import lombok.ToString
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Entity
@Getter
@ToString
class Member(
    @field: Column(nullable = false, unique = true)
    @field: Email
    @field: NotBlank
    var email: String,

    @field: NotBlank
    @field: Column(nullable = false)
    @field : JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String,

    @field: NotBlank
    var name: String,

    @field: NotBlank
    @field: Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
    var birth: String,
    @field: NotBlank
    var address: String,
    @Enumerated(EnumType.STRING)
    var gender: Gender,
    @field: NotBlank
    var profile_img_url: String,

    @Enumerated(EnumType.STRING)
    val authority : Authority
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "patient", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var matching: Matching? = null

    @OneToMany(mappedBy = "member", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var diagnosisList: MutableList<DiagnosisResult> = mutableListOf()

    @OneToMany(
        mappedBy = "consultPatient",
        targetEntity = Consulting::class,
        cascade = [CascadeType.REMOVE],
        orphanRemoval = true
    )
    var consultingList: MutableList<Consulting> = mutableListOf()

    @OneToMany(
        mappedBy = "checkPatient",
        targetEntity = CheckList::class,
        cascade = [CascadeType.REMOVE],
        orphanRemoval = true
    )
    var checkLists: MutableList<CheckList> = mutableListOf()

    @OneToMany(
        mappedBy = "dastMember",
        targetEntity = Dast::class,
        cascade = [CascadeType.REMOVE],
        orphanRemoval = true
    )
    var dastLists : MutableList<Dast> = mutableListOf()

}
