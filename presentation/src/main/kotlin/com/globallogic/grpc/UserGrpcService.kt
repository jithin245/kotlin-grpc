package com.globallogic.grpc

import com.globallogic.entity.Gender
import com.globallogic.entity.User
import com.globallogic.service.UserService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired


@GRpcService
class UserGrpcService(
    @Autowired private val userServiceImpl: UserService
) : UserServiceGrpc.UserServiceImplBase() {

    override fun createUser(
        request: CreateUserRequest?,
        responseObserver: StreamObserver<CreateUserResponse>?
    ) {
        val createdUser = userServiceImpl.createUser(buildUser(request))
        responseObserver?.onNext(buildUserResponse(createdUser))
        responseObserver?.onCompleted()
    }

    override fun authenticateUserCredentials(
        request: AuthenticateRequest?,
        responseObserver: StreamObserver<AuthenticateResponse>?
    ) {
        val isAuthenticated = userServiceImpl.authenticateUserCredentials(request?.userName, request?.password)
        val authenticationMessage = if (isAuthenticated) "USER AUTH SUCCESS" else "USER AUTH FAILED"
        responseObserver?.onNext(buildAuthenticationResponse(authenticationMessage))
        responseObserver?.onCompleted()
    }
}

fun buildUser(userRequest: CreateUserRequest?) =
    User(id = null,
        firstName = userRequest?.firstName ?: "",
        lastName = userRequest?.lastName ?: "",
        gender = Gender.valueOf(userRequest?.gender.toString()),
        email = userRequest?.email ?: "",
        contact = userRequest?.contact ?: "",
        password = userRequest?.password ?: ""
        )

fun buildUserResponse(user: User): CreateUserResponse =
    CreateUserResponse
        .newBuilder()
        .setUser(
            user_proto.User.newBuilder()
                .setId(user.id ?: 0)
                .setFirstName(user.firstName)
                .setLastName(user.lastName)
                .setGender(user_proto.Gender.valueOf(user.gender.toString()))
                .setEmail(user.email)
                .setContact(user.contact)
                .setPassword(user.password)
                .build()
        )
        .build()

fun buildAuthenticationResponse(message: String): AuthenticateResponse =
    AuthenticateResponse
        .newBuilder()
        .setMessage(message)
        .build()
