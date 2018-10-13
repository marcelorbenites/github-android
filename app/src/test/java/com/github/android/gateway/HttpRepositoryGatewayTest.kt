package com.github.android.gateway

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals

class HttpRepositoryGatewayTest : Spek({

    describe("HttpRepositoryGateway") {
        it("should get repositories via HTTP") {

            val server = MockWebServer()

            server.enqueue(
                MockResponse()
                    .setBody("Fake JSON")
                    .setResponseCode(200)
            )

            server.start()

            val jsonParser = mock<JsonParser>()
            val repositoryGateway = HttpRepositoryGateway.Builder()
                .setBaseUrl(server.url("/").toString())
                .setJsonParser(jsonParser)
                .setUsername("john.lennon@beatles.com")
                .setPassword("1234")
                .build()

            whenever(jsonParser.parseRepositories(any())).thenReturn(emptyList())

            repositoryGateway.getRepositories("quick", "asc", 1, 30)

            val request = server.takeRequest()

            assertEquals("GET", request.method)
            assertEquals(
                "/search/repositories?sort=quick&order=asc&page=1&per_page=30",
                request.path
            )
            assertEquals(
                "Basic am9obi5sZW5ub25AYmVhdGxlcy5jb206MTIzNA==",
                request.getHeader("Authorization")
            )
            verify(jsonParser).parseRepositories("Fake JSON")

            server.shutdown()
        }
    }
})

