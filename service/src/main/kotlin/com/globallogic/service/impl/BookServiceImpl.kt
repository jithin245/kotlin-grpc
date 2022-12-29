package com.globallogic.service.impl

import com.globallogic.entity.Book
import com.globallogic.repository.BookRepository
import com.globallogic.service.BookService
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookServiceImpl
@Autowired constructor(
    private val bookRepository: BookRepository
) : BookService {

    companion object : Logging

    override fun createBook(book: Book): Book {
        logger.info("[BOOKSVC] saving book details ${book.bookTitle}")
        return bookRepository.save(book)
    }

    override fun getAllBooks(): List<Book?> {
        logger.info("[BOOKSVC] fetching all book details")
        return bookRepository.findAll()
    }

    override fun get(bookId: Int): Book? {
        logger.info("[BOOKSVC] fetching book details of ${bookId}")
        val optionalBook = bookRepository.findById(bookId)
        return if (!optionalBook.isPresent) {
            null
        } else optionalBook.get()
    }

    override fun getBookByTitleRatingsAndPublishingYear(
        bookTitle: String?,
        ratings: Double,
        publishingYear: Int
    ): List<Book?>? {
        return bookRepository.findByBookTitleContainingAndRatingsAndPublishingYear(bookTitle, ratings, publishingYear)
    }
}