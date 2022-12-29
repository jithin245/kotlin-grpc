package com.globallogic.grpc

import io.grpc.ManagedChannelBuilder
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class UserGrpcServiceTest {

    companion object {
        private val channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build()
        private val stub = UserServiceGrpc.newBlockingStub(channel);
    }


    @Test
    fun createUser() {
        val request = CreateUserRequest.newBuilder()
            .setFirstName("deepthy")
            .setLastName("george")
            .setGender(user_proto.Gender.FEMALE)
            .setContact("9645518098")
            .setEmail("deepthy.george@globallogic.com")
            .setPassword("password")
            .build();
        val response = stub.createUser(request);
        assertEquals("deepthy", response.user.firstName)
    }

}
