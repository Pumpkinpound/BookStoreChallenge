package com.example.demo.repository.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_table")
class UserDetailEntity {

    @Id
    @Column
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    var userId: Int = 0

    @Column
    @JsonIgnore
    var username = ""
    @Column
    var password = ""
        @JsonIgnore
        get() = field
        set(value) {
            var passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }
    @Column
    var name = ""
    @Column
    var surname = ""
    @Column
    var dateOfBirth = ""
    @Column
    var books = ""

    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }

}