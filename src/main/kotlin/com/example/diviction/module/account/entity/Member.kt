package com.example.diviction.module.account.entity

import com.example.diviction.module.constant.Gender
import lombok.Getter
import lombok.ToString
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
}
