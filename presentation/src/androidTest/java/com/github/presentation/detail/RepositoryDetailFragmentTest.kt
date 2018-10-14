package com.github.presentation.detail

import android.support.annotation.StringRes
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.domain.repository.Repositories
import com.github.domain.repository.Repository
import com.github.presentation.FakeNavigator
import com.github.presentation.FakeRepositoryManager
import com.github.presentation.TestActivity
import com.github.presentation.TestApplication
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryDetailFragmentTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    private lateinit var testApplication: TestApplication

    @Before
    fun setUp() {
        testApplication = (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication)
    }

    @Test
    fun shouldDisplaySelectedRepositoryWhenRepositoryDetailViewIsDisplayed() {
        val selectedRepository = Repository(
            "1",
            "Imagine Language",
            "Programming language.",
            "John Lennon"
        )
        testApplication.repositoryManager = FakeRepositoryManager(
            Repositories(
                listOf(
                    selectedRepository
                ),
                Repositories.Status.LOADED,
                selectedRepository
            )
        )

        (rule.activity as TestActivity).testNavigator = mock()
        (rule.activity as TestActivity).showFragment(RepositoryDetailFragment())

        onView(withText("Imagine Language")).check(matches(isDisplayed()))
        onView(withText("Programming language.")).check(matches(isDisplayed()))
        onView(withText("John Lennon")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldClearSelectedRepositoryAndNavigateBackToRepositoryListOnBackPressed() {
        val selectedRepository = Repository(
            "1",
            "Imagine Language",
            "Programming language.",
            "John Lennon"
        )
        val repositoryManagerSpy = spy(FakeRepositoryManager(
            Repositories(
                listOf(selectedRepository),
                Repositories.Status.LOADED,
                selectedRepository
            )
        ))
        testApplication.repositoryManager = repositoryManagerSpy

        val navigatorSpy = spy(FakeNavigator())

        (rule.activity as TestActivity).testNavigator = navigatorSpy
        (rule.activity as TestActivity).showFragment(RepositoryDetailFragment())

        Espresso.onIdle {
            navigatorSpy.simulateBackPressed()
        }

        Espresso.onIdle {
            verify(repositoryManagerSpy).clearSelectedRepository()
            verify(navigatorSpy).navigateBack()
        }
    }

    private fun getString(@StringRes stringResource: Int) =
        InstrumentationRegistry.getTargetContext().getString(stringResource)
}
