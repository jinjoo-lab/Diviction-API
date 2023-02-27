package com.example.diviction.module.account.entity

import com.example.diviction.module.constant.Gender
import com.example.diviction.module.consulting.entity.Consulting
import lombok.Getter
import lombok.ToString
import org.springframework.format.annotation.DateTimeFormat
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Entity
@Getter
@ToString
class Counselor(
    @field: Email
    @field: NotBlank
    var email : String,
    @field: NotBlank
    var password : String,
    @field: NotBlank
    var name : String,
    @field: NotBlank
    @field: Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
    var birth : String,
    @field: NotBlank
    var address : String,
    @Enumerated(EnumType.STRING)
    var gender : Gender,
    @field: NotBlank
    var profile_img_url : String

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    var confirm : Boolean = false
        set(value) {
            field = value
        }

    @OneToMany(mappedBy = "counselor", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var matching_list : MutableList<Matching> =  mutableListOf()

    @OneToMany(mappedBy = "consultCounselor", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var consultingList : MutableList<Consulting> = mutableListOf()
}
