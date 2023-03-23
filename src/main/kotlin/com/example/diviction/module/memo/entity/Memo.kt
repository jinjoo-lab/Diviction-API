package com.example.diviction.module.memo.entity

import com.example.diviction.module.account.entity.Matching
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Getter
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Getter
@Table(name = "memo")
class Memo(
    @NotBlank
    var title : String,
    @NotBlank
    var content : String,

    @Column(name = "init_dt")
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val initDate : LocalDateTime,

    @Column(name = "modi_dt")
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var modiDate : LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    val match : Matching
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
}
