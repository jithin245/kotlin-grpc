package com.globallogic.entity

import javax.persistence.*

@Entity
@Table(name = "books")
data class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    val bookTitle: String,
    val publishingYear: Int,
    val ratings: Double,
    val price: Double,
    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: Author
) {
    override fun toString(): String {
        return "Book(id=$id, bookTitle='$bookTitle', publishingYear=$publishingYear, ratings=$ratings, price=$price)"
    }
}