package com.example.diviction.module.audit.entity

import com.example.diviction.module.account.entity.Member
import javax.persistence.*

import javax.validation.constraints.Pattern

@Entity
@Table(name = "audit")
class Audit() {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var member: Member? = null

    @Column(name = "date")
    @field: Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
    var date: String? = null

    @Column(name = "q1")
    var q1: Int? = null

    @Column(name = "score")
    var score: Int? = null
}