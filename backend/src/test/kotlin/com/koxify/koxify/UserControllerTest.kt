package com.koxify.koxify

import com.koxify.koxify.user.User
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
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

    @Test
    fun postUser_whenUserIsValid_recieveOk() {
        val user = User(
            username = "test-user",
            displayName = ("test-display"),
            password = ("test-password")
        )

        val response = testRestTemplate.postForEntity("/api/1.0/users", user, Object::class.java)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }
}