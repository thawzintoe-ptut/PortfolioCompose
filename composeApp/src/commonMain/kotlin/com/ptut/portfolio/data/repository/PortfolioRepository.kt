package com.ptut.portfolio.data.repository

import com.ptut.portfolio.data.model.PortfolioData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import portfoliocompose.composeapp.generated.resources.Res

class PortfolioRepository {

    private val _data = MutableStateFlow<PortfolioData?>(null)
    val data: StateFlow<PortfolioData?> = _data.asStateFlow()

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun load() {
        val bytes = Res.readBytes("files/portfolio_data.json")
        val text = bytes.decodeToString()
        _data.value = json.decodeFromString<PortfolioData>(text)
    }
}
