package com.ptut.portfolio

import com.ptut.portfolio.data.model.Experience
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.data.model.Profile
import com.ptut.portfolio.data.model.Project
import com.ptut.portfolio.data.model.Skill
import com.ptut.portfolio.data.model.Stat
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PortfolioModelsTest {

    private val sampleProfile = Profile(
        name = "Jane Doe",
        title = "Android Engineer",
        subtitle = "5 Years · Kotlin · Compose",
        bio = "Passionate mobile developer.",
        email = "jane@example.com",
        github = "https://github.com/janedoe",
        linkedin = "https://linkedin.com/in/janedoe",
    )

    private val sampleSkills = listOf(
        Skill(id = "sk1", name = "Kotlin", category = "Language", iconName = "kotlin"),
        Skill(id = "sk2", name = "Compose", category = "UI", iconName = "compose"),
        Skill(id = "sk3", name = "Ktor", category = "Network", iconName = "ktor"),
        Skill(id = "sk4", name = "SwiftUI", category = "UI", iconName = "swiftui"),
    )

    private val sampleProjects = listOf(
        Project(
            id = "p1",
            title = "Portfolio App",
            description = "This very app.",
            tags = listOf("Kotlin", "Compose Multiplatform"),
            imageUrl = "",
            githubUrl = "https://github.com/janedoe/portfolio",
            liveUrl = "",
        ),
        Project(
            id = "p2",
            title = "Weather App",
            description = "Real-time weather data.",
            tags = listOf("Ktor", "Android"),
            imageUrl = "",
            githubUrl = "https://github.com/janedoe/weather",
            liveUrl = "https://weather.janedoe.dev",
        ),
    )

    private val sampleExperiences = listOf(
        Experience(
            id = "e1",
            company = "Acme Corp",
            role = "Senior Android Engineer",
            duration = "2022 – Present",
            achievements = listOf("Led team of 5", "Shipped 3 major releases"),
        ),
        Experience(
            id = "e2",
            company = "Beta Inc",
            role = "Android Developer",
            duration = "2019 – 2022",
            achievements = listOf("Migrated codebase to Kotlin"),
        ),
    )

    private val samplePortfolioData = PortfolioData(
        profile = sampleProfile,
        stats = listOf(Stat("Years", "5+"), Stat("Projects", "20+")),
        skills = sampleSkills,
        projects = sampleProjects,
        experiences = sampleExperiences,
    )

    // --- Profile tests ---

    @Test
    fun profileHoldsCorrectValues() {
        assertEquals("Jane Doe", sampleProfile.name)
        assertEquals("Android Engineer", sampleProfile.title)
        assertEquals("jane@example.com", sampleProfile.email)
    }

    @Test
    fun profileCopyUpdatesField() {
        val updated = sampleProfile.copy(name = "John Doe")
        assertEquals("John Doe", updated.name)
        assertEquals(sampleProfile.title, updated.title)
    }

    // --- Stat tests ---

    @Test
    fun statHoldsLabelAndValue() {
        val stat = Stat(label = "Years", value = "5+")
        assertEquals("Years", stat.label)
        assertEquals("5+", stat.value)
    }

    // --- Skill grouping tests ---

    @Test
    fun skillsGroupByCategory() {
        val grouped = sampleSkills.groupBy { it.category }
        assertEquals(3, grouped.size)
        assertEquals(listOf("Kotlin"), grouped["Language"]?.map { it.name })
        assertEquals(setOf("Compose", "SwiftUI"), grouped["UI"]?.map { it.name }?.toSet())
        assertEquals(listOf("Ktor"), grouped["Network"]?.map { it.name })
    }

    @Test
    fun skillsChunkedFor2Columns() {
        val uiSkills = sampleSkills.filter { it.category == "UI" }
        val rows = uiSkills.chunked(2)
        assertEquals(1, rows.size)
        assertEquals(2, rows[0].size)
    }

    @Test
    fun skillsChunkedFor3Columns_padded() {
        val allSkills = sampleSkills // 4 skills
        val rows = allSkills.chunked(3)
        assertEquals(2, rows.size)
        assertEquals(3, rows[0].size)
        assertEquals(1, rows[1].size) // last row has 1 item, remaining 2 should be spacers
    }

    // --- Project tests ---

    @Test
    fun projectHasCorrectTags() {
        assertEquals(listOf("Kotlin", "Compose Multiplatform"), sampleProjects[0].tags)
    }

    @Test
    fun projectLiveUrlEmptyCheck() {
        assertTrue(sampleProjects[0].liveUrl.isEmpty())
        assertTrue(sampleProjects[1].liveUrl.isNotEmpty())
    }

    @Test
    fun projectsChunkedFor2Columns() {
        val rows = sampleProjects.chunked(2)
        assertEquals(1, rows.size)
        assertEquals(2, rows[0].size)
    }

    // --- Experience tests ---

    @Test
    fun experienceHoldsAchievements() {
        val exp = sampleExperiences[0]
        assertEquals(2, exp.achievements.size)
        assertEquals("Led team of 5", exp.achievements[0])
    }

    @Test
    fun lastExperienceDetection() {
        val last = sampleExperiences.last()
        assertEquals("e2", last.id)
        assertTrue(sampleExperiences.lastIndex == 1)
    }

    // --- PortfolioData aggregation tests ---

    @Test
    fun portfolioDataHoldsAllSections() {
        assertEquals(2, samplePortfolioData.stats.size)
        assertEquals(4, samplePortfolioData.skills.size)
        assertEquals(2, samplePortfolioData.projects.size)
        assertEquals(2, samplePortfolioData.experiences.size)
    }

    @Test
    fun portfolioDataProfileAccessible() {
        assertEquals("Jane Doe", samplePortfolioData.profile.name)
    }

    @Test
    fun nullablePortfolioDataDefaultsToEmpty() {
        val data: PortfolioData? = null
        val skills = data?.skills ?: emptyList()
        val projects = data?.projects ?: emptyList()
        val experiences = data?.experiences ?: emptyList()
        assertTrue(skills.isEmpty())
        assertTrue(projects.isEmpty())
        assertTrue(experiences.isEmpty())
    }
}
