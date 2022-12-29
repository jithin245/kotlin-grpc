package com.globallogic.repository

import com.globallogic.entity.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book?, Int?> {
    fun findByBookTitleContainingAndRatingsAndPublishingYear(
        bookTitle: String?,
        ratings: Double,
        publishingYear: Int
    ): List<Book?>?
}