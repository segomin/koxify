package com.koxify.koxify.user

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class User(
    @field:NotBlank(message = "Should not be Empty")
    val username: String,
    @field:NotBlank(message = "Should not be Empty")
    val displayName: String,
    @field:NotBlank(message = "Should not be Empty")
    val password: String,
) {
    @Id
    @GeneratedValue
    var id: Long = -1
}