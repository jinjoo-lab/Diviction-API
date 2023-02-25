package com.example.diviction.module.account.entity

import com.example.diviction.module.constant.MatchState
import lombok.Getter
import javax.persistence.*

@Entity
@Getter
class Match(


) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Enumerated(value = EnumType.STRING)
    var state : MatchState = MatchState.KEEP
}
