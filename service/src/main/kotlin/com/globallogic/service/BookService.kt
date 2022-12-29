package com.globallogic.service

import com.globallogic.entity.Book

interface BookService {
    fun createBook(book: Book): Book
    fun getAllBooks(): List<Book?>
    fun get(bookId: Int): Book?
    fun getBookByTitleRatingsAndPublishingYear(bookTitle: String?, ratings: Double, publishingYear: Int): List<Book?>?
}