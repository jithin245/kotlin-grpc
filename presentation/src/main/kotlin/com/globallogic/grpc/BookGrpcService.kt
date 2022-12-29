package com.globallogic.grpc

import com.globallogic.entity.Author
import com.globallogic.entity.Book
import com.globallogic.service.BookService
import com.globallogic.service.impl.BookServiceImpl
import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired

@GRpcService
class BookGrpcService (
    @Autowired private val bookService: BookService
) : BookServiceGrpc.BookServiceImplBase() {

    override fun createBook(request: CreateBookRequest?, responseObserver: StreamObserver<BookResponse>?) {
        val createdAuthor = bookService.createBook(buildBook(request))
        responseObserver?.onNext(buildBookResponse(createdAuthor))
        responseObserver?.onCompleted()
    }

    override fun getAllBooks(request: Empty?, responseObserver: StreamObserver<BooksResponse>?) {
        val allBooks = bookService.getAllBooks();
        responseObserver?.onNext(buildAllBooksResponse(allBooks));
        responseObserver?.onCompleted();
    }

    override fun getBookById(request: GetBookByIdRequest?, responseObserver: StreamObserver<BookResponse>?) {
        request?.let {
            val book = bookService.get(it.id);
            responseObserver?.onNext(buildBookResponse(book ?: emptyBook()))
            responseObserver?.onCompleted()
        };
    }

    override fun searchBook(request: SearchBookRequest?, responseObserver: StreamObserver<BooksResponse>?) {
        request?.let {
            val books = bookService.getBookByTitleRatingsAndPublishingYear(
                bookTitle = request.bookTitle ?: "",
                publishingYear = request.publishingYear,
                ratings = request.ratings
            )
            responseObserver?.onNext(buildAllBooksResponse(books ?: ArrayList()))
            responseObserver?.onCompleted()
        }
    }
}

fun emptyBook() : Book =
    Book(id = 0,
        bookTitle = "",
        publishingYear = 0,
        ratings = 0.0,
        price = 0.0,
        author = Author(id = 0, authorName = "", age = 0, numberOfBooksWritten = 0, books = ArrayList()))

fun buildBook(request: CreateBookRequest?): Book  =
    Book(id = null,
        bookTitle = request?.bookTitle ?: "",
        publishingYear = request?.publishingYear ?: 0,
        ratings = request?.ratings ?: 0.0,
        price = request?.price ?: 0.0,
        author = Author(id = request?.authorId ?: 0,
                        authorName = "",
                        age = 0,
                        numberOfBooksWritten = 0,
                        books = ArrayList())
    )

fun buildBookResponse(createdAuthor: Book): BookResponse? =
    BookResponse.newBuilder()
        .setBook(book_proto.Book.newBuilder()
            .setId(createdAuthor.id ?: 0)
            .setBookTitle(createdAuthor.bookTitle)
            .setPublishingYear(createdAuthor.publishingYear)
            .setRatings(createdAuthor.ratings)
            .setPrice(createdAuthor.price)
            .setAuthor(book_proto.AuthorInfo.newBuilder()
                .setId(createdAuthor.author?.id ?: 0)
                .setAge(createdAuthor.author.age)
                .setAuthorName(createdAuthor.author.authorName)
                .setNumberOfBooksWritten(createdAuthor.author.numberOfBooksWritten)
                .build())
            .build())
        .build();

fun buildAllBooksResponse(allBooks: List<Book?>): BooksResponse? {
    val newBuilder = BooksResponse.newBuilder()
    allBooks
        .forEach {
            newBuilder.addBook(
                book_proto.Book.newBuilder()
                    .setId(it?.id ?: 0)
                    .setBookTitle(it?.bookTitle ?: "")
                    .setPublishingYear(it?.publishingYear ?: 0)
                    .setRatings(it?.ratings ?: 0.0)
                    .setPrice(it?.price ?: 0.0)
                    .setAuthor(book_proto.AuthorInfo.newBuilder()
                        .setId(it?.author?.id ?: 0)
                        .setAge(it?.author?.age ?: 0)
                        .setAuthorName(it?.author?.authorName ?: "")
                        .setNumberOfBooksWritten(it?.author?.numberOfBooksWritten ?: 0)
                        .build())
                    .build()
            )
        };
    return newBuilder.build()
}
