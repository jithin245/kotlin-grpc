package com.globallogic.service.impl

import com.globallogic.entity.User
import com.globallogic.repository.UserRepository
import com.globallogic.service.UserService
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils

@Service
class UserServiceImpl
@Autowired constructor(
    private val userRepository: UserRepository
) : UserService {

    companion object : Logging

    override fun createUser(user: User): User {
        logger.info("[USERSVC] saving user details ${user.email}")
        return userRepository.save(user)
    }


    override fun authenticateUserCredentials(userName: String?, password: String?): Boolean {
        val userList: List<User?>? = userRepository.findByEmailAndPassword(userName, password)
        logger.info("[USERSVC] validated user credentials $userName")
        return !CollectionUtils.isEmpty(userList)
    }

}
