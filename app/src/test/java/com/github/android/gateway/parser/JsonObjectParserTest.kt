package com.github.android.gateway.parser

import com.github.android.repository.Repository
import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class JsonObjectParserTest : Spek({

    describe("JsonObjectParser") {
        it("should parse repositories") {
            val parser = JsonObjectParser()

            val repositories = listOf(
                Repository(
                    "Ruby",
                    "Ruby is a scripting language designed for simplified object-oriented programming.",
                    "Yukihiro Matsumoto"
                ),
                Repository(
                    "Rails",
                    "Ruby on Rails (Rails) is a web application framework written in Ruby.",
                    "David Heinemeier Hansson"
                ),
                Repository(
                    "Homebrew",
                    "Homebrew is a package manager for macOS.",
                    "Max Howell"
                )
            )

            val json = """
                        {
                          "total_count": 6,
                          "incomplete_results": false,
                          "items": [
                            {
                              "id": 1,
                              "name": "Ruby",
                              "description": "Ruby is a scripting language designed for simplified object-oriented programming.",
                              "owner": {
                                "login": "Yukihiro Matsumoto"
                              }
                            },
                            {
                              "id": 2,
                              "name": "Rails",
                              "description": "Ruby on Rails (Rails) is a web application framework written in Ruby.",
                              "owner": {
                                "login": "David Heinemeier Hansson"
                              }
                            },
                            {
                              "id": 3,
                              "name": "Homebrew",
                              "description": "Homebrew is a package manager for macOS.",
                              "owner": {
                                "login": "Max Howell"
                              }
                            }
                          ]
                        }
                    """.trimIndent()

            assertEquals(repositories, parser.parseRepositories(json))
        }
    }
})

