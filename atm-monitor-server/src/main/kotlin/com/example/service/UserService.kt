package com.example.service

import com.example.repository.UserRepository
import com.example.routing.request.UserRequest
import com.example.routing.response.UserResponse

class UserService(private val userRepository: UserRepository) {


    fun getAllUsers() : List<UserResponse> {
        return userRepository.findAll()
    }

    fun addNewUser(request: UserRequest) :String? {
        return userRepository.addNewUser(request)

    }

    fun getByUserName(name:String): UserResponse? {
        return userRepository.getUserByName(name)
    }

}