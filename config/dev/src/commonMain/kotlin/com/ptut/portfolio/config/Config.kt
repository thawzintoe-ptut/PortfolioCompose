package com.ptut.portfolio.config

object Config {
    const val ENVIRONMENT: String = "DEV"
    const val APP_NAME: String = "Portfolio (Dev)"
    const val BASE_URL: String = "https://dev-api.ptut.com"
    const val IS_DEBUG: Boolean = true

    // Firebase config for Web & Desktop
    object Firebase {
        const val API_KEY: String = "AIzaSyDiD2Iq033V97OB9BIDVqspOxjemW05Q6U"
        const val AUTH_DOMAIN: String = "portfolio-dev-5798f.firebaseapp.com"
        const val PROJECT_ID: String = "portfolio-dev-5798f"
        const val STORAGE_BUCKET: String = "portfolio-dev-5798f.firebasestorage.app"
        const val MESSAGING_SENDER_ID: String = "978564042528"
        const val APP_ID: String = "1:978564042528:web:c60c3a531e5db8c33b9145"
        const val MEASUREMENT_ID: String = "G-NEL72SWSRG"
    }
}
