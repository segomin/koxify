package com.koxify.koxify.user

import org.intellij.lang.annotations.RegExp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
data class User(
    @field:NotBlank
    @field:Size(min = 4, max = 255)
    val username: String,
    @field:NotBlank
    @field:Size(min = 4, max = 255)
    val displayName: String,
    @field:NotBlank
    @field:Size(min = 8, max = 255)
    @field:Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    val password: String,
) {
    @Id
    @GeneratedValue
    var id: Long = -1
}