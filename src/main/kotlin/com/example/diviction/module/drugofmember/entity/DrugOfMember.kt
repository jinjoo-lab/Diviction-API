package com.example.diviction.module.drugofmember.entity

import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.drug.entity.Drug
import lombok.ToString
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "drug_member")
@ToString
class DrugOfMember(
        @ManyToOne
        @JoinColumn(name = "member_id")
        var member: Member? = null,

        @ManyToOne
        @JoinColumn(name = "product_id")
        var drug: Drug? = null
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}