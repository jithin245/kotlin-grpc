package com.globallogic.repository

import com.globallogic.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByEmailAndPassword(userName: String?, password: String?): List<User?>?
}