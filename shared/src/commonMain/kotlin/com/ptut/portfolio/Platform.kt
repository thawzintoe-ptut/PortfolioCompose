package com.ptut.portfolio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform