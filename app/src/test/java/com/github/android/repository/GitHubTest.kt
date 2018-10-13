package com.github.android.repository

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class GitHubTest : Spek({

    describe("GitHub") {
        it("should load trending repositories") {

            val repositoryGatewayMock = mock<RepositoryGateway>()
            val listenerMock = mock<RepositoryListener>()

            val gitHub = GitHub(
                repositoryGatewayMock,
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                mutableListOf(),
                0,
                2
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

            val captor = argumentCaptor<List<Repository>>()

            verify(listenerMock).onRepositoryUpdate(captor.capture())
            assertEquals(captor.lastValue, repositories)
        }
    }
})
