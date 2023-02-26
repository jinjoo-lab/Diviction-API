package com.example.diviction.module.account.entity

import com.example.diviction.module.constant.Gender
import lombok.Getter
import lombok.ToString
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Getter
@ToString
class Counselor(
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

    var confirm : Boolean = false
        set(value) {
            field = value
        }

    @OneToMany(mappedBy = "counselor", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var matching_list : MutableList<Matching> =  mutableListOf()
}
