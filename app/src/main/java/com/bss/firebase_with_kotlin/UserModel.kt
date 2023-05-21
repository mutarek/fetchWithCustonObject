package com.bss.firebase_with_kotlin

data class UserModel(
    val name: String? = null,
    val number: Number? = null,
    val fName: String? = null,
    val lName: String? = null,
    @field:JvmField val isVerified: Boolean? = null,
    val password: String? = null

)