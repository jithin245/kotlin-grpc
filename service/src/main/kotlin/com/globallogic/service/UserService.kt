package com.globallogic.service

import com.globallogic.entity.User

interface UserService {
    fun createUser(user: User): User
    fun authenticateUserCredentials(userName: String?, password: String?): Boolean
}