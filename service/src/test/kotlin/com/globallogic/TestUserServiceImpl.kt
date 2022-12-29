package com.globallogic

import com.globallogic.entity.Gender
import com.globallogic.entity.User
import com.globallogic.repository.UserRepository
import com.globallogic.service.impl.UserServiceImpl
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.Answer
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class TestUserServiceImpl {
    @InjectMocks
    private val userService: UserServiceImpl? = null

    @Mock
    private val userRepository: UserRepository? = null

    @Test
    fun testCreateUser() {
        val user = User(id = null, firstName = "Deepthy", lastName = "George", email = "deepthy.george@globallogic.com",
            password = "password", gender = Gender.FEMALE, contact = "9900919937")
        Mockito.`when`<Any>(userRepository!!.save(Mockito.any()))
            .thenAnswer(Answer<Any> { invocationOnMock: InvocationOnMock ->
                val userMock = invocationOnMock.arguments[0] as User
                userMock.id = 1
                userMock
            })
        assertEquals(1, userService?.createUser(user)?.id)
    }
}