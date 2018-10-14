package com.github.android.view

import android.support.annotation.StringRes
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.android.GitHubActivity
import com.github.android.R
import com.github.android.TestApplication
import com.github.android.repository.FakeRepositoryManager
import com.github.android.repository.Repositories
import com.github.android.repository.Repository
import com.github.android.view.ViewMatchersExtension.Companion.withHolderView
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
    fun shouldDisplayLoadedTrendingRepositories() {
        testApplication.repositoryManager = FakeRepositoryManager(
            Repositories(listOf(
                Repository("1", "Imagine Language", "Programming language.", "John Lennon")
            ), Repositories.Status.LOADED)
        )

        testApplication.repositoryManager!!.loadTrendingRepositories()

        rule.launchActivity(null)

        onView(withText("Imagine Language")).check(matches(isDisplayed()))
        onView(withText("Programming language.")).check(matches(isDisplayed()))
        onView(withText("John Lennon")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayLoadingWhileTrendingRepositoriesAreNotLoaded() {
        testApplication.repositoryManager = FakeRepositoryManager(
            Repositories(emptyList(), Repositories.Status.LOADING)
        )

        testApplication.repositoryManager!!.loadTrendingRepositories()

        rule.launchActivity(null)

        onView(withText(getString(R.string.fragment_repository_list_loading_text))).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayErrorWhenTrendingRepositoriesFailToLoad() {
        testApplication.repositoryManager = FakeRepositoryManager(
            Repositories(emptyList(), Repositories.Status.ERROR)
        )

        testApplication.repositoryManager!!.loadTrendingRepositories()

        rule.launchActivity(null)

        onView(withText(getString(R.string.fragment_repository_list_error_text))).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNavigateToRepositoryDetailViewWhenRepositorySelected() {
        val selectedRepository = Repository("1", "Imagine Language", "Programming language.", "John Lennon")
        testApplication.repositoryManager = FakeRepositoryManager(
            Repositories(listOf(
                selectedRepository,
                Repository("2", "Super Project", "Super magic fantastic project.", "Proud Guy"),
                Repository("3", "My Project", "My my my project.", "Selfish Guy")
            ), Repositories.Status.LOADED),
            selectedRepository
        )

        testApplication.repositoryManager!!.loadTrendingRepositories()

        rule.launchActivity(null)

        onView(withId(R.id.fragment_repository_list))
            .perform(actionOnHolderItem(withHolderView(hasDescendant(withText("Imagine Language"))),
                ViewActions.click()))

        onView(withText(getString(R.string.fragment_repository_detail_title))).check(matches(isDisplayed()))
    }

    private fun getString(@StringRes stringResource: Int) =
        InstrumentationRegistry.getTargetContext().getString(stringResource)
}
