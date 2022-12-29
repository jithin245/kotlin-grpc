package com.globallogic.service

import com.globallogic.entity.Author

interface AuthorService {
    fun createAuthor(author: Author): Author
    fun getAllAuthors(): List<Author>
    fun get(authorId: Int): Author?
}