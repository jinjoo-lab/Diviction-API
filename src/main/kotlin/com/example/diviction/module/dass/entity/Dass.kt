package com.example.diviction.module.dass.entity

import com.example.diviction.module.account.entity.Member
import javax.persistence.*
import javax.validation.constraints.Pattern

@Entity
@Table(name = "dass")
class Dass() {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null

    @ManyToOne
    @JoinColumn(name = "member_id")
    var member : Member? = null

    @Column(name = "date")
    @field: Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
    var date : String? = null

    @Column(name = "melancholy_score")
    var melancholyScore : Int? = null

    @Column(name = "unrest_score")
    var unrestScore : Int? = null

    @Column(name = "stress_score")
    var stressScore : Int? = null
}