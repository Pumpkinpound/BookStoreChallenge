package com.example.demo.repository.entity

import javax.persistence.*

@Entity
@Table(name = "books_List_table")
class BooksEntity(
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    var bookId: Int?,

    @Column(name = "book_name")
    var book_name: String,

    @Column(name = "author_name")
    var author_name: String,

    @Column(name = "price")
    var price: Int,

    @Column(name = "is_recommended")
    var is_recommended: Boolean? = false

//    @Column(name = "is_recommended")
//    var isRecommended: Boolean,
)