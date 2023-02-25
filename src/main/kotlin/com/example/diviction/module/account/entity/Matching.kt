package com.example.diviction.module.account.entity

import com.example.diviction.module.constant.MatchState
import lombok.Getter
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Getter
class Matching(
    @ManyToOne()
    @JoinColumn(nullable = false,name = "patient_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var patient : Member,

    @ManyToOne()
    @JoinColumn(nullable = false,name = "counselor_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var counselor : Counselor

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Enumerated(value = EnumType.STRING)
    var state : MatchState = MatchState.KEEP
}
