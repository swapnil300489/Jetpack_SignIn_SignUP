package com.jetpack.loginsignup.req_res_model

data class UserListResponse (
    val address: Address,
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val name: Name,
    val phone: String,
    val __v: Int
)

data class Address(
    val geolocation: Geolocation,
    val city: String,
    val street: String,
    val number: Int,
    val zipcode: String
)

data class Geolocation(
    val lat: String,
    val long: String
)

data class Name(
    val firstname: String,
    val lastname: String
)