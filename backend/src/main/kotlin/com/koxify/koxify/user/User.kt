package com.koxify.koxify.user

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(
    val username: String,
    val displayName: String,
    val password: String,
) {
    @Id
    @GeneratedValue
    var id: Long = -1
}