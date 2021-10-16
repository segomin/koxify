package com.koxify.koxify

import com.koxify.koxify.shared.GenericResponse
import com.koxify.koxify.user.User
import com.koxify.koxify.user.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

//@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for TestRestTemplate
@ActiveProfiles("test")
@TestPropertySource(
    properties =
    ["spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"]
)
class UserControllerTest(
        @Autowired val testRestTemplate: TestRestTemplate,
        @Autowired val userRepository: UserRepository
) {
    @BeforeEach
    internal fun cleanup() {
        userRepository.deleteAll()
    }

    @Test // methodName_condition_expectBehaviour
    fun `post valid user should receive Ok`() {
        val user = createValidUser()

        val response = postSignup(user, Object::class.java)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `post valid user should save to database`() {
        val user = createValidUser()

        postSignup(user, Object::class.java)
        Assertions.assertThat(userRepository.count()).isEqualTo(1)
    }

    @Test
    fun `post valid user should receive success message`() {
        val user = createValidUser()

        val response: ResponseEntity<GenericResponse> = postSignup(user, GenericResponse::class.java)
        Assertions.assertThat(response.body?.message).isNotNull
    }

    @Test
    fun `post valid user should password hashed in database`() {
        // given
        val user = createValidUser()

        // when
        postSignup(user, Any::class.java)
        val inDB = userRepository.findAll()[0]

        // then
        Assertions.assertThat(inDB.password).isNotEqualTo(user.password)
    }

    @Test
    fun `post user with empty username should receive bad request`() {
        // given
        val user = createValidUser().copy(username = "")
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with empty display name should receive bad request`() {
        // given
        val user = createValidUser().copy(displayName = "")
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with empty password should receive bad request`() {
        // given
        val user = createValidUser().copy(password = "")
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with username less than required, should receive bad request`() {
        // given
        val user = createValidUser().copy(username = "abc")
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with displayname less than required, should receive bad request`() {
        // given
        val user = createValidUser().copy(displayName = "abc")
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with password less than required, should receive bad request`() {
        // given
        val user = createValidUser().copy(password = "P4sswd")
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with username exceeds the limit length, should receive bad request`() {
        // given
        val valueOf256Char = (1..256).map { 'a' }.joinToString("")
        val user = createValidUser().copy(username = valueOf256Char)
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with display exceeds the limit length, should receive bad request`() {
        // given
        val valueOf256Char = (1..256).map { 'a' }.joinToString("")
        val user = createValidUser().copy(displayName = valueOf256Char)
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with password exceeds the limit length, should receive bad request`() {
        // given
        val valueOf256Char = (1..256).map { 'a' }.joinToString("")
        val user = createValidUser().copy(password = valueOf256Char + "A1")
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with password all lowercase, should receive bad request`() {
        // given
        val user = createValidUser().copy(password = "alllowercase")
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with password all uppercase, should receive bad request`() {
        // given
        val user = createValidUser().copy(password = "ALLLOWERCASE")
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `post user with password all number, should receive bad request`() {
        // given
        val user = createValidUser().copy(password = "123456789")
        // when
        val response = postSignup(user, Any::class.java)
        // then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    fun <T>postSignup(request: Any, response: Class<T>): ResponseEntity<T> {
        return testRestTemplate.postForEntity(API_1_0_USERS, request, response)
    }

    private fun createValidUser(): User {
        return User(
            username = "test-user",
            displayName = ("test-display"),
            password = ("P4ssword")
        )
    }

    companion object {
        const val API_1_0_USERS = "/api/1.0/users"
    }
}