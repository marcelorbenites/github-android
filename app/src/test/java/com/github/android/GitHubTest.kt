package com.github.android

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class GitHubTest : Spek({

    describe("GitHub") {
        it("should query trending repositories") {
            val repositories = listOf(Repository(), Repository())
            val gitHub = GitHub(
                FakeRepositoryGateway(repositories),
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                mutableListOf()
            )

            val listenerMock = mock<RepositoryListener>()

            gitHub.registerListener(listenerMock)
            gitHub.loadTrendingRepositories()

            val captor = argumentCaptor<List<Repository>>()

            verify(listenerMock).onRepositoryUpdate(captor.capture())
            assertEquals(captor.lastValue, repositories)
        }
    }
})
