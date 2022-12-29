package com.globallogic.service.impl

import com.globallogic.entity.Author
import com.globallogic.repository.AuthorRepository
import com.globallogic.service.AuthorService
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl
@Autowired constructor(
    private val authorRepository: AuthorRepository
) : AuthorService {

    companion object : Logging

    override fun createAuthor(author: Author): Author {
        logger.log(Level.INFO,"[AUTHORSVC] savings author details ${author.authorName}")
        return authorRepository.save(author)
    }

    override fun getAllAuthors(): List<Author> {
        logger.log(Level.INFO, "[AUTHORSVC] getting all author details ")
        return authorRepository.findAll()
    }

    override fun get(authorId: Int): Author? {
        logger.log(Level.INFO, "[AUTHORSVC] fetching author details  $authorId ")
        val optionalAuthor = authorRepository.findById(authorId)
        return if (!optionalAuthor.isPresent) {
            null
        } else optionalAuthor.get()
    }
}