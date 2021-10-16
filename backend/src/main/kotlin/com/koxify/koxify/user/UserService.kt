package com.koxify.koxify.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun saveUser(user: User): User {
        val encodedUser = user.let { it.copy(password = passwordEncoder.encode((user.password))) }
        return userRepository.save(encodedUser)
    }
}