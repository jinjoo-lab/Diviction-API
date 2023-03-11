package com.example.diviction.module.DAST.entity

import com.example.diviction.module.account.entity.Member
import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Getter
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Getter
@Table(name = "dast")
class Dast(
    @field: NotBlank
    val drug : String,

    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date : LocalDate,

    @field: NotBlank
    val frequency : Long,

    @field: NotBlank
    val injection : Long,

    @field: NotBlank
    val cure : Long,

    @field: NotBlank
    val question : Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val dastMember : Member
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

}
