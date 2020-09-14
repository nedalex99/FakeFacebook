package com.teme.fakefacebook.registration.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var firstName: String? = null,
    var lastName: String? = null,
    var year: String? = null,
    var month: String? = null,
    var day: String? = null,
    var gender: String? = null,
    var mobileNumber: String? = null,
    var password: String? = null,
    var email: String? = null
) : Parcelable