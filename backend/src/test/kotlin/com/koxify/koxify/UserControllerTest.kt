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
    fun `post valid user should receive Ok`() {
        val user = createValidUser()

        val response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object::class.java)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `post valid user should save to database`() {
        val user = createValidUser()

        testRestTemplate.postForEntity(API_1_0_USERS, user, Object::class.java)
        Assertions.assertThat(userRepository.count()).isEqualTo(1)
    }

    @Test
    fun `post valid user should receive success message`() {
        val user = createValidUser()

        val response: ResponseEntity<GenericResponse> = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse::class.java)
        Assertions.assertThat(response.body!!.message).isNotNull
    }

    @Test
    fun `post valid user should password hashed in database`() {
        // given
        val user = createValidUser()

        // when
        testRestTemplate.postForEntity(API_1_0_USERS, user, Object::class.java)
        val inDB = userRepository.findAll()[0]

        // then
        Assertions.assertThat(inDB.password).isNotEqualTo(user.password)
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