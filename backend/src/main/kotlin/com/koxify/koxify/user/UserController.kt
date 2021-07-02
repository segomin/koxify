package com.koxify.koxify.user

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @PostMapping("/api/1.0/users")
    fun createUser() {
    }
}