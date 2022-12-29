package com.globallogic.entity

import javax.persistence.*

@Entity
@Table(name = "authors")
data class Author (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    val authorName: String,
    val age: Int,
    val numberOfBooksWritten: Int,
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val books: List<Book> = ArrayList()
) {
    override fun toString(): String {
        return "Author(id=$id, authorName='$authorName', age=$age, numberOfBooksWritten=$numberOfBooksWritten)"
    }
}
