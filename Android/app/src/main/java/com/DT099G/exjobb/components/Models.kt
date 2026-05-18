package com.DT099G.exjobb.components

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    var id: Int,
    var accountNumber: String,
    var accountBalance: Double,
    var currency: String
)

@Serializable
data class Page(
    var pageName: String,
    var destination: String,
    var id: String = pageName
)