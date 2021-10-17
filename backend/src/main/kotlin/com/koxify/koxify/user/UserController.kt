package com.koxify.koxify.user

import com.koxify.koxify.error.ApiError
import com.koxify.koxify.shared.GenericResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import kotlin.collections.mutableMapOf
import kotlin.collections.mapOf

@RestController
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/api/1.0/users")
    fun createUser(@Valid @RequestBody user: User): GenericResponse {
        userService.saveUser(user)
        return GenericResponse("User saved")
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(exception: MethodArgumentNotValidException, request: HttpServletRequest): ApiError {
        val errors: MutableMap<String, String> = mutableMapOf<String, String>()
        exception.bindingResult.fieldErrors.forEach {
            errors[it.field] = it.defaultMessage ?: ""
        }

        return ApiError(status = 400,
            message = "Validation error",
            url = request.servletPath,
            validationErrors = errors.toMap())
    }

}