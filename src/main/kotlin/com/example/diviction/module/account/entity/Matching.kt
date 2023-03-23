package com.example.diviction.module.account.entity

import com.example.diviction.module.constant.MatchState
import com.example.diviction.module.memo.entity.Memo
import lombok.Getter
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Getter
class Matching(
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "patient_id")
    var patient : Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "counselor_id")
    var counselor : Counselor

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Enumerated(value = EnumType.STRING)
    var state : MatchState = MatchState.KEEP

    @OneToMany(
        targetEntity = Memo::class,
        mappedBy = "match",
        cascade = [CascadeType.REMOVE],
        orphanRemoval = true
    )
    var memoList : MutableList<Memo> = mutableListOf()
}
