package com.koxify.koxify.error

import org.springframework.boot.context.properties.bind.validation.ValidationErrors

data class ApiError (
    val timestamp: Long = System.currentTimeMillis(),
    val status: Int,
    val message: String,
    val url: String,
    val validationErrors: Map<String, String>
)