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
class UserControllerTest {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate
    @Autowired
    private lateinit var  userRepository: UserRepository

    @BeforeEach
    internal fun setUp() {
    }

    @AfterEach
    internal fun tearDown() {
        userRepository.deleteAll()
    }

    @Test // methodName_condition_expectBehaviour
    fun postUser_whenUserIsValid_receiveOk() {
        val user = createValidUser()

        val response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object::class.java)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun postUser_whenUserIsValid_userSavedToDatabase() {
        val user = createValidUser()

        testRestTemplate.postForEntity(API_1_0_USERS, user, Object::class.java)
        Assertions.assertThat(userRepository.count()).isEqualTo(1)
    }

    @Test
    fun postUser_whenUserIsValid_receiveSuccessMessage() {
        val user = createValidUser()

        val response: ResponseEntity<GenericResponse> = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse::class.java)
        Assertions.assertThat(response.body!!.message).isNotNull
    }

    private fun createValidUser(): User {
        val user = User(
            username = "test-user",
            displayName = ("test-display"),
            password = ("test-password")
        )
        return user
    }

    companion object {
        const val API_1_0_USERS = "/api/1.0/users"
    }
}