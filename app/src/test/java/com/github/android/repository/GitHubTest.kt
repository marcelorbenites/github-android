package com.github.android.repository

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import java.io.IOException

class GitHubTest : Spek({

    describe("GitHub") {
        it("should load trending repositories") {

            val repositoryGatewayMock = mock<RepositoryGateway>()
            val listenerMock = mock<RepositoryListener>()

            val gitHub = GitHub(
                repositoryGatewayMock,
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                0,
                2,
                Repositories()
            )

            val repositories = listOf(
                Repository(
                    "Rails",
                    "Ruby is a scripting language designed for simplified object-oriented programming.",
                    "Yukihiro Matsumoto"
                ),
                Repository(
                    "Rails",
                    "Ruby on Rails (Rails) is a web application framework written in Ruby.",
                    "David Heinemeier Hansson"
                )
            )

            whenever(repositoryGatewayMock.getRepositories("star", "desc", 0, 2))
                .thenReturn(repositories)

            gitHub.registerListener(listenerMock)
            gitHub.loadTrendingRepositories()

            val captor = argumentCaptor<Repositories>()

            verify(listenerMock, times(3)).onRepositoriesUpdate(captor.capture())
            assertEquals(captor.firstValue, Repositories(emptyList(), Repositories.Status.IDLE))
            assertEquals(captor.secondValue, Repositories(emptyList(), Repositories.Status.LOADING))
            assertEquals(captor.lastValue, Repositories(repositories, Repositories.Status.LOADED))
        }

        it("should emit current trending repositories when listener is registered") {
            val repositoryGatewayMock = mock<RepositoryGateway>()
            val listenerMock = mock<RepositoryListener>()

            val gitHub = GitHub(
                repositoryGatewayMock,
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                0,
                2,
                Repositories()
            )

            val repositories = listOf(
                Repository(
                    "Rails",
                    "Ruby is a scripting language designed for simplified object-oriented programming.",
                    "Yukihiro Matsumoto"
                ),
                Repository(
                    "Rails",
                    "Ruby on Rails (Rails) is a web application framework written in Ruby.",
                    "David Heinemeier Hansson"
                )
            )

            whenever(repositoryGatewayMock.getRepositories("star", "desc", 0, 2))
                .thenReturn(repositories)

            gitHub.loadTrendingRepositories()
            gitHub.registerListener(listenerMock)

            val captor = argumentCaptor<Repositories>()

            verify(listenerMock).onRepositoriesUpdate(captor.capture())
            assertEquals(captor.lastValue, Repositories(repositories, Repositories.Status.LOADED))
        }

        it("should emit repositories with error status when repository gateway fails") {
            val repositoryGatewayMock = mock<RepositoryGateway>()
            val listenerMock = mock<RepositoryListener>()

            val gitHub = GitHub(
                repositoryGatewayMock,
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                0,
                2,
                Repositories()
            )

            whenever(repositoryGatewayMock.getRepositories("star", "desc", 0, 2))
                .thenAnswer { IOException() }

            gitHub.loadTrendingRepositories()
            gitHub.registerListener(listenerMock)

            val captor = argumentCaptor<Repositories>()

            verify(listenerMock).onRepositoriesUpdate(captor.capture())
            assertEquals(captor.lastValue, Repositories(emptyList(), Repositories.Status.ERROR))
        }

        it("should not load if repositories are already loading") {
            val repositoryGatewayMock = mock<RepositoryGateway>()
            val testScheduler = TestScheduler()

            val gitHub = GitHub(
                repositoryGatewayMock,
                testScheduler,
                Schedulers.trampoline(),
                0,
                2,
                Repositories()
            )

            whenever(repositoryGatewayMock.getRepositories("star", "desc", 0, 2))
                .thenReturn(emptyList())

            gitHub.loadTrendingRepositories()
            gitHub.loadTrendingRepositories()
            gitHub.loadTrendingRepositories()

            testScheduler.triggerActions()

            verify(repositoryGatewayMock, times(1)).getRepositories("star", "desc", 0, 2)
        }
    }
})
