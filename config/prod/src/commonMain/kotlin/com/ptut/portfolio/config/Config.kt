package com.ptut.portfolio.config

object Config {
    const val ENVIRONMENT: String = "PROD"
    const val APP_NAME: String = "Portfolio"
    const val BASE_URL: String = "https://api.ptut.com"
    const val IS_DEBUG: Boolean = false

    // Firebase config for Web & Desktop
    object Firebase {
        const val API_KEY: String = "AIzaSyDJ5Tg8dZbkS5wjz_Kysmp_uTIFNt8_ldg"
        const val AUTH_DOMAIN: String = "portfolio-8afbd.firebaseapp.com"
        const val PROJECT_ID: String = "portfolio-8afbd"
        const val STORAGE_BUCKET: String = "portfolio-8afbd.firebasestorage.app"
        const val MESSAGING_SENDER_ID: String = "45736644578"
        const val APP_ID: String = "1:45736644578:web:0ac612dd1fe5ab869cb3bb"
        const val MEASUREMENT_ID: String = "G-RNMY3CY2QW"
    }
}
