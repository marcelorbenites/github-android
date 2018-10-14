package com.github.android.view.detail

import android.support.annotation.StringRes
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.android.R
import com.github.android.TestApplication
import com.github.android.repository.FakeRepositoryManager
import com.github.android.repository.Repositories
import com.github.android.repository.Repository
import com.github.android.view.GitHubActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryDetailFragmentTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<GitHubActivity> = ActivityTestRule(GitHubActivity::class.java, false, false)

    private lateinit var testApplication: TestApplication

    @Before
    fun setUp() {
        testApplication = (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication)
    }

    @Test
    fun shouldDisplaySelectedRepositoryDetail() {
        val selectedRepository = Repository("1", "Imagine Language", "Programming language.", "John Lennon")
        testApplication.repositoryManager = FakeRepositoryManager(
            Repositories(listOf(
                selectedRepository
            ), Repositories.Status.LOADED, selectedRepository)
        )

        testApplication.repositoryManager!!.loadTrendingRepositories()

        rule.launchActivity(null)

        onView(withText(getString(R.string.fragment_repository_detail_title))).check(matches(isDisplayed()))
        onView(withText("Imagine Language")).check(matches(isDisplayed()))
        onView(withText("Programming language.")).check(matches(isDisplayed()))
        onView(withText("John Lennon")).check(matches(isDisplayed()))
    }

    private fun getString(@StringRes stringResource: Int) =
        InstrumentationRegistry.getTargetContext().getString(stringResource)
}
