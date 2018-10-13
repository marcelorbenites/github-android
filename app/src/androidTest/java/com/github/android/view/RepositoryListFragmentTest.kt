package com.github.android.view

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.android.GitHubActivity
import com.github.android.TestApplication
import com.github.android.repository.FakeRepositoryManager
import com.github.android.repository.Repository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryListFragmentTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<GitHubActivity> = ActivityTestRule(GitHubActivity::class.java, false, false)

    private lateinit var testApplication: TestApplication

    @Before
    fun setUp() {
        testApplication = (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication)
    }

    @Test
    fun shouldDisplayLoadingTrendingRepositories() {
        testApplication.repositoryManager = FakeRepositoryManager(
            listOf(
                Repository("Imagine Language", "Programming language.", "John Lennon")
            )
        )

        testApplication.repositoryManager!!.loadTrendingRepositories()

        rule.launchActivity(null)

        onView(withText("Imagine Language")).check(matches(isDisplayed()))
        onView(withText("Programming language.")).check(matches(isDisplayed()))
        onView(withText("John Lennon")).check(matches(isDisplayed()))
    }
}
