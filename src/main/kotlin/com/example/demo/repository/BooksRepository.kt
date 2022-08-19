package com.example.demo.repository

import com.example.demo.repository.entity.BooksEntity
import com.example.demo.repository.entity.UserDetailEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BooksRepository: JpaRepository<BooksEntity, Int> {
    fun getByBookId(bookId: Int): BooksEntity

    @Query("SELECT * FROM ChallengeBookStore.books_list_table ORDER BY is_recommended DESC, book_name ASC", nativeQuery = true)
    fun getBookInformationOrder(): Array<BooksEntity>
}