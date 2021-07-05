package com.koxify.koxify.user

import com.koxify.koxify.shared.GenericResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @Autowired
    lateinit private var userService: UserService

    @PostMapping("/api/1.0/users")
    fun createUser(@RequestBody user: User): GenericResponse {
        userService.saveUser(user)
        return GenericResponse("User saved")
    }
}