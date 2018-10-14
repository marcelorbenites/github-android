package com.github.presentation.list

import android.support.annotation.StringRes
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
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
import com.github.domain.repository.Repositories
import com.github.domain.repository.Repository
import com.github.presentation.FakeNavigator
import com.github.presentation.FakeRepositoryManager
import com.github.presentation.R
import com.github.presentation.TestActivity
import com.github.presentation.TestApplication
import com.github.presentation.ViewMatchersExtension.Companion.withHolderView
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryListFragmentTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    private lateinit var testApplication: TestApplication

    @Before
    fun setUp() {
        testApplication = (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication)
    }

    @Test
    fun shouldDisplayLoadedTrendingRepositoriesWhenRepositoryListViewIsDisplayed() {
        testApplication.repositoryManager = FakeRepositoryManager(
            Repositories(listOf(
                Repository("1", "Imagine Language", "Programming language.", "John Lennon")
            ), Repositories.Status.LOADED)
        )

        (rule.activity as TestActivity).testNavigator = mock()
        (rule.activity as TestActivity).showFragment(RepositoryListFragment())

        onView(withText("Imagine Language")).check(matches(isDisplayed()))
        onView(withText("Programming language.")).check(matches(isDisplayed()))
        onView(withText("John Lennon")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayLoadingWhileTrendingRepositoriesAreNotLoaded() {
        testApplication.repositoryManager = FakeRepositoryManager(
            Repositories(emptyList(), Repositories.Status.LOADING)
        )

        (rule.activity as TestActivity).testNavigator = mock()
        (rule.activity as TestActivity).showFragment(RepositoryListFragment())

        onView(withText(getString(R.string.fragment_repository_list_loading_text))).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayErrorWhenTrendingRepositoriesFailToLoad() {
        testApplication.repositoryManager = FakeRepositoryManager(
            Repositories(emptyList(), Repositories.Status.ERROR)
        )

        (rule.activity as TestActivity).testNavigator = mock()
        (rule.activity as TestActivity).showFragment(RepositoryListFragment())

        onView(withText(getString(R.string.fragment_repository_list_error_text))).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNavigateToRepositoryDetailViewWhenRepositorySelected() {
        val selectedRepository = Repository("1", "Imagine Language", "Programming language.", "John Lennon")
        val repositoryManagerSpy = spy(
            FakeRepositoryManager(
                Repositories(listOf(
                    selectedRepository,
                    Repository("2", "Super Project", "Super magic fantastic project.", "Proud Guy"),
                    Repository("3", "My Project", "My my my project.", "Selfish Guy")
                ), Repositories.Status.LOADED),
                selectedRepository
            )
        )
        testApplication.repositoryManager = repositoryManagerSpy

        val navigatorMock = mock<FakeNavigator>()

        (rule.activity as TestActivity).testNavigator = navigatorMock
        (rule.activity as TestActivity).showFragment(RepositoryListFragment())

        onView(withId(R.id.fragment_repository_list))
            .perform(actionOnHolderItem(withHolderView(hasDescendant(withText("Imagine Language"))),
                ViewActions.click()))

        Espresso.onIdle {
            verify(repositoryManagerSpy).selectRepository("1")
            verify(navigatorMock).navigateToRepositoryDetailView()
        }
    }

    private fun getString(@StringRes stringResource: Int) =
        InstrumentationRegistry.getTargetContext().getString(stringResource)
}
