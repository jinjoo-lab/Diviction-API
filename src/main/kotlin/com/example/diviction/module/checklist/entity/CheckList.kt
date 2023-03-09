package com.example.diviction.module.checklist.entity

import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.constant.CheckListState
import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Getter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.util.Date
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Getter
@Table(name = "checklist")
class CheckList(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    val checkPatient : Member,

    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="start_date")
    val startDate : LocalDate,

    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="end_date")
    val endDate : LocalDate,

    @field: NotBlank
    var content : String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Enumerated(EnumType.STRING)
    var state : CheckListState = CheckListState.BEFORE
}
