package com.pawcare.client

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform