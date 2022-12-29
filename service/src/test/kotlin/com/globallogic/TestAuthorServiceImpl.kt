package com.globallogic

import com.globallogic.entity.Author
import com.globallogic.repository.AuthorRepository
import com.globallogic.service.impl.AuthorServiceImpl
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class TestAuthorServiceImpl {
    @InjectMocks
    private val authorService: AuthorServiceImpl? = null

    @Mock
    private val authorRepository: AuthorRepository? = null

    @Test
    fun findAuthor() {
        val author = Author(id = 1, numberOfBooksWritten = 5, authorName = "JK Rowling", age = 45)
        Mockito.`when`(authorRepository!!.findById(1))
            .thenReturn(Optional.of(author))
        assertEquals("JK Rowling", authorService!!.get(1)!!.authorName)
    }

    @Test
    fun testCreateAuthor() {
        val author = Author(id = null, numberOfBooksWritten = 5, authorName = "JK Rowling", age = 45)
        Mockito.`when`<Any>(authorRepository!!.save(Mockito.any()))
            .thenAnswer { invocationOnMock: InvocationOnMock ->
                val authorMock = invocationOnMock.arguments[0] as Author
                authorMock.id = 5
                authorMock
            }
        assertEquals(5, authorService?.createAuthor(author)?.id)
    }

    @Test
    fun testGetAllAuthors() {
        val authorOne = Author(id = null, numberOfBooksWritten = 5, authorName = "JK Rowling", age = 45)
        val authorTwo = Author(id = null, numberOfBooksWritten = 9, authorName = "George R.R Martin", age = 70)
        val authors = mutableListOf(authorOne, authorTwo)
        Mockito.`when`(authorRepository?.findAll())
            .thenReturn(authors)
        assertEquals(2, authorService?.getAllAuthors()?.size)
    }
}