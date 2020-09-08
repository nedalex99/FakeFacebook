package com.teme.fakefacebook.models

data class User(
    var firstName: String,
    var lastName: String,
    var year: String,
    var month: String,
    var day: String,
    var gender: String,
    var mobileNumber: String,
    var password: String,
    var email: String?
)