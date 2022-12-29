package com.globallogic

import com.globallogic.entity.Author
import com.globallogic.entity.Book
import com.globallogic.repository.BookRepository
import com.globallogic.service.impl.BookServiceImpl
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.Answer
import java.util.*
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class TestBookServiceImpl {
    @InjectMocks
    private val bookService: BookServiceImpl? = null

    @Mock
    private val bookRepository: BookRepository? = null

    @Test
    fun createBook() {
        val book = Book(id = null, bookTitle = "Harry porter", publishingYear = 2000,
            price = 50.0, ratings = 4.5, author = Author(id = 1, authorName = "", age = 0, numberOfBooksWritten = 0)
        )
        Mockito.`when`<Any>(bookRepository?.findById(1))
            .thenReturn(Optional.of(book))
        assertEquals("Harry porter", bookService?.get(1)?.bookTitle)
    }

    @Test
    fun testCreateBook() {
        val book = Book(id = null, bookTitle = "Harry porter", publishingYear = 2000,
            price = 50.0, ratings = 4.5, author = Author(id = 1, authorName = "", age = 0, numberOfBooksWritten = 0)
        )
        Mockito.`when`<Any>(bookRepository?.save(Mockito.any()))
            .thenAnswer(Answer<Any> { invocationOnMock: InvocationOnMock ->
                val bookMock = invocationOnMock.arguments[0] as Book
                bookMock.id = 1
                bookMock
            })
        assertEquals(1, bookService?.createBook(book)?.id)
    }

    @Test
    fun testGetAllBooks() {
        val bookOne = Book(id = null, bookTitle = "Harry porter", publishingYear = 2000,
            price = 50.0, ratings = 4.5, author = Author(id = 1, authorName = "", age = 0, numberOfBooksWritten = 0)
        )
        val bookTwo = Book(id = null, bookTitle = "Harry porter 2", publishingYear = 2000,
            price = 50.0, ratings = 4.5, author = Author(id = 1, authorName = "", age = 0, numberOfBooksWritten = 0)
        )
        val books = mutableListOf(bookOne ?: null, bookTwo ?: null)
        Mockito.`when`(bookRepository?.findAll())
            .thenReturn(books)
        assertEquals(2, bookService?.getAllBooks()?.size)
    }
}