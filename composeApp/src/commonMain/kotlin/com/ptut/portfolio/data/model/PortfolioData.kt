package com.ptut.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val name: String,
    val title: String,
    val subtitle: String,
    val bio: String,
    val email: String,
    val github: String,
    val linkedin: String,
    val philosophy: String = "",
)

@Serializable
data class Stat(
    val label: String,
    val value: String,
)

@Serializable
data class Skill(
    val id: String,
    val name: String,
    val category: String,
    val iconName: String,
)

@Serializable
data class Project(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
    val imageUrl: String,
    val githubUrl: String,
    val liveUrl: String,
)

@Serializable
data class Experience(
    val id: String,
    val company: String,
    val role: String,
    val duration: String,
    val location: String = "",
    val achievements: List<String>,
)

@Serializable
data class PortfolioData(
    val profile: Profile,
    val stats: List<Stat>,
    val skills: List<Skill>,
    val projects: List<Project>,
    val experiences: List<Experience>,
)
