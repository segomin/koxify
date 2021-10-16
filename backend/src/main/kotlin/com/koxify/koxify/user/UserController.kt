package com.koxify.koxify.user

import com.koxify.koxify.shared.GenericResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/api/1.0/users")
    fun createUser(@Valid @RequestBody user: User): GenericResponse {
        userService.saveUser(user)
        return GenericResponse("User saved")
    }
}