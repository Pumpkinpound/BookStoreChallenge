package com.example.demo.service

import com.example.demo.model.*
import com.example.demo.repository.BooksRepository
import com.example.demo.repository.UsersDetailRepository
import com.example.demo.repository.entity.BooksEntity
import com.example.demo.repository.entity.UserDetailEntity
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Service
class UserDetailService(
    private val usersDetailRepository: UsersDetailRepository,
    private val booksRepository: BooksRepository
) {
    fun save(user: UserDetailEntity): UserDetailEntity {
        return this.usersDetailRepository.save(user)
    }

    fun createUserAccountService(body: Register): ResponseEntity<Any> {
        try {
        val user = UserDetailEntity()
        user.username = body.username
        user.password = body.password
        user.dateOfBirth = body.date_of_birth
        return ResponseEntity.ok(save(user)) }
        catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Message("Username already exists"))
        }
    }

    fun loginUserAccountService(body: Login, response: HttpServletResponse): ResponseEntity<Any> {
        val user = usersDetailRepository.findByUsername(body.username)
        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message("username is null"))
        }
        if(!user.comparePassword(body.password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message("password is wrong"))
        }
        val cookie = loginUserAccountCookieService(user)
        response.addCookie(cookie)
        return ResponseEntity.ok(Message("success"))
    }

    fun loginUserAccountCookieService(user: UserDetailEntity): Cookie {
        val issuer = user.userId.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // 1 day
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        return cookie
    }

    fun getInformationUsersService(jwt: String?): ResponseEntity<Any> {
        try {
            if (jwt == null) {
                return ResponseEntity.status(401).body(Message("unauthenticated"))
            }
            val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body
            return ResponseEntity.ok(usersDetailRepository.getByUserId(body.issuer.toInt()))
        } catch (ex: Exception) {
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }
    }

    fun deleteUserInformationService(jwt: String?, response: HttpServletResponse): ResponseEntity<Any> {
        try {
            if (jwt == null) {
                return ResponseEntity.status(401).body(Message("unauthenticated"))
            }
            println("here")
            val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body
            val cookie = Cookie("jwt", "")
            cookie.maxAge = 0
            response.addCookie(cookie)
            println(body.issuer)
            usersDetailRepository.deleteByUserId(body.issuer.toInt())
            return ResponseEntity.ok().body(Message("OK"))
        } catch (ex: Exception) {
            return ResponseEntity.status(500).body(Message("database problem"))
        }
    }

    fun priceCalculatingService(jwt: String?, body: OrderBooks): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }
        var sum: Int = 0
        try {
            for(i in body.books) {
                sum = sum + booksRepository.getByBookId(i).price
            }
        } catch (ex: Exception) {
            return ResponseEntity.status(400).body(Message("invalid request bookid"))
        }
        return ResponseEntity.ok().body(Price(sum))
    }

    fun updateLocalBookStoreInfo(x: EachBook, isRecommendedStatus: Boolean) {
        booksRepository.save(
            BooksEntity(
                bookId = x.id,
                book_name = x.book_name,
                author_name = x.author_name,
                price = x.price,
                is_recommended = isRecommendedStatus
            )
        )
    }

    fun getBooksInformationService(): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body(booksRepository.getBookInformationOrder())
    }
}