package com.example.diviction.module.drug.entity

import lombok.Builder
import lombok.Getter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
@Getter
@Builder
class Drug (
        @field: NotBlank
        var name : String
        ){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null

    fun updateName(name : String){
        this.name = name
    }
}