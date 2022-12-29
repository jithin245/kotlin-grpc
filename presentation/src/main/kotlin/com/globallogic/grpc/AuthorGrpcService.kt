package com.globallogic.grpc

import com.globallogic.entity.Author
import com.globallogic.grpc.book_proto.Book
import com.globallogic.service.AuthorService
import com.globallogic.service.impl.AuthorServiceImpl
import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired

@GRpcService
class AuthorGrpcService(
    @Autowired private val authorService: AuthorService
) : AuthorServiceGrpc.AuthorServiceImplBase() {
    override fun createAuthor(request: CreateAuthorRequest?, responseObserver: StreamObserver<CreateAuthorResponse>?) {
        val createdAuthor = authorService.createAuthor(buildAuthor(request))
        responseObserver?.onNext(buildCreateAuthorResponse(createdAuthor))
        responseObserver?.onCompleted()
    }

    override fun getAllAuthors(request: Empty?, responseObserver: StreamObserver<AuthorsResponse>?) {
        val allAuthors = authorService.getAllAuthors();
        responseObserver?.onNext(buildAllAuthorsResponse(allAuthors));
        responseObserver?.onCompleted();
    }

    override fun getAuthorById(request: GetAuthorByIdRequest?, responseObserver: StreamObserver<AuthorResponse>?) {
        request?.let {
            val author = authorService.get(request.id)
            responseObserver?.onNext(buildAuthorResponse(author ?: emptyAuthor()))
            responseObserver?.onCompleted()
        }
    }
}

fun emptyAuthor(): Author =
    Author(id = 0,
        authorName = "",
        numberOfBooksWritten = 0,
        age = 0,
        books = ArrayList())

fun buildAuthor(request: CreateAuthorRequest?): Author =
    Author(
        id = null,
        authorName = request?.authorName ?: "",
        numberOfBooksWritten = request?.numberOfBooksWritten ?: 0,
        age = request?.numberOfBooksWritten ?: 0,
        books = ArrayList())

fun buildAuthorResponse(author: Author): AuthorResponse? {
    return AuthorResponse.newBuilder()
        .setAuthor(
            author_proto.Author.newBuilder()
                .setId(author.id ?: 0)
                .setAge(author.age)
                .setAuthorName(author.authorName)
                .setNumberOfBooksWritten(author.numberOfBooksWritten)
                .addAllBooks(author.books
                    .map { book ->
                        Book.newBuilder()
                            .setId(book.id ?: 0)
                            .setBookTitle(book.bookTitle)
                            .setPrice(book.price)
                            .setRatings(book.ratings)
                            .setPublishingYear(book.publishingYear)
                            .build()
                    }
                )
                .build()
        )
        .build();
}

fun buildCreateAuthorResponse(createdAuthor: Author): CreateAuthorResponse? =
    CreateAuthorResponse.newBuilder()
        .setId(createdAuthor.id ?: 0)
        .setAge(createdAuthor.age)
        .setAuthorName(createdAuthor.authorName)
        .setNumberOfBooksWritten(createdAuthor.numberOfBooksWritten)
        .build();

fun buildAllAuthorsResponse(allAuthors: List<Author?>): AuthorsResponse? {
    val newBuilder = AuthorsResponse.newBuilder()
    allAuthors
        .forEach {
            newBuilder.addAuthor(
                author_proto.Author.newBuilder()
                    .setId(it?.id ?: 0)
                    .setAuthorName(it?.authorName ?: "")
                    .setAge(it?.age ?: 0)
                    .setNumberOfBooksWritten(it?.numberOfBooksWritten ?: 0)
                    .addAllBooks(it?.books?.map { book ->
                        book_proto.Book.newBuilder()
                            .setId(book?.id ?: 0)
                            .setBookTitle(book.bookTitle)
                            .setPrice(book.price)
                            .setRatings(book.ratings)
                            .setPublishingYear(book.publishingYear)
                            .build()
                    })
                    .build()
            )
        };
    return newBuilder.build()
}
