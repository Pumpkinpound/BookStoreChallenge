package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.repository.BooksRepository
import com.example.demo.repository.UsersDetailRepository
import com.example.demo.service.UserDetailService
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
class BookStoreController(
    private val userService: UserDetailService,
    private val usersDetailRepository: UsersDetailRepository,
    private val booksRepository: BooksRepository
) {

    @PostMapping("/users")
    fun createUserAccount(@RequestBody body: Register): ResponseEntity<Any> {
        return userService.createUserAccountService(body)
    }

    @PostMapping("/login")
    fun loginUserAccount(@RequestBody body: Login, response: HttpServletResponse): ResponseEntity<Any> {
        return userService.loginUserAccountService(body,response)
    }

    @GetMapping("/users")
    fun getInformationUser(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        return userService.getInformationUsersService(jwt)
    }

    @DeleteMapping("/users")
    fun deleteUser(@CookieValue("jwt") jwt: String?,response: HttpServletResponse): ResponseEntity<Any> {
        return userService.deleteUserInformationService(jwt, response)
    }

    @PostMapping("/users/orders")
    fun orderBooks(@CookieValue("jwt") jwt: String?, @RequestBody body: OrderBooks): ResponseEntity<Any> {
        return userService.priceCalculatingService(jwt,body)
    }

    @GetMapping("/books")
    fun getBooksInformation(): ResponseEntity<Any> {
        return userService.getBooksInformationService()
    }
}