package com.example.demo.repository

import com.example.demo.repository.entity.UserDetailEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UsersDetailRepository: JpaRepository<UserDetailEntity, Int> {
    fun findByUsername(username: String): UserDetailEntity?
    fun getByUserId(userId: Int): UserDetailEntity
    @Transactional
    fun deleteByUserId(userId: Int)
}