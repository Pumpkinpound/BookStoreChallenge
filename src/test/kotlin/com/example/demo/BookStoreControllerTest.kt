package com.example.demo

import com.example.demo.helpers.BookStoreControllerTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@Profile("test")
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookStoreControllerTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    @DisplayName("200 : create a user and store user's information success")
    fun createUserAccountTest() {
        val uri = BookStoreControllerTestHelper.buildRequestUri(port,"users")
        val requestPost = BookStoreControllerTestHelper.buildIdentityBulkRequestEntity()

        val result = restTemplate.postForEntity(uri, requestPost, String::class.java)

        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.statusCode)
    }
}