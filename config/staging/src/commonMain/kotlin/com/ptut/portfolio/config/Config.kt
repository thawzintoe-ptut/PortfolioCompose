package com.ptut.portfolio.config

object Config {
    const val ENVIRONMENT: String = "STAGING"
    const val APP_NAME: String = "Portfolio (Staging)"
    const val BASE_URL: String = "https://staging-api.ptut.com"
    const val IS_DEBUG: Boolean = true

    // Firebase config for Web & Desktop
    object Firebase {
        const val API_KEY: String = "AIzaSyBt1gK8tXOuuElxBIqMjVCBapvrg9Ydv2w"
        const val AUTH_DOMAIN: String = "portfolio-staging-2964b.firebaseapp.com"
        const val PROJECT_ID: String = "portfolio-staging-2964b"
        const val STORAGE_BUCKET: String = "portfolio-staging-2964b.firebasestorage.app"
        const val MESSAGING_SENDER_ID: String = "412306417639"
        const val APP_ID: String = "1:412306417639:web:c613bd0808bd7ea860bf42"
        const val MEASUREMENT_ID: String = "G-5H71NYK0RJ"
    }
}
