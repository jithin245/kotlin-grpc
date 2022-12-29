package com.globallogic.entity

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val email: String,
    val contact: String,
    val password: String
)
