package com.koxify.koxify.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun saveUser(user: User): User {
        return userRepository.save(user)
    }
}