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
                              "name": "ruby",
                              "display_name": "Ruby",
                              "short_description": "Ruby is a scripting language designed for simplified object-oriented programming.",
                              "description": "Ruby was developed by Yukihiro \"Matz\" Matsumoto in 1995 with the intent of having an easily readable programming language. It is integrated with the Rails framework to create dynamic web-applications. Ruby's syntax is similar to that of Perl and Python.",
                              "created_by": "Yukihiro Matsumoto",
                              "released": "December 21, 1995",
                              "created_at": "2016-11-28T22:03:59Z",
                              "updated_at": "2017-10-30T18:16:32Z",
                              "featured": true,
                              "curated": true,
                              "score": 1750.5872
                            },
                            {
                              "name": "rails",
                              "display_name": "Rails",
                              "short_description": "Ruby on Rails (Rails) is a web application framework written in Ruby.",
                              "description": "Ruby on Rails (Rails) is a web application framework written in Ruby. It is meant to help simplify the building of complex websites.",
                              "created_by": "David Heinemeier Hansson",
                              "released": "December 13 2005",
                              "created_at": "2016-12-09T17:03:50Z",
                              "updated_at": "2017-10-30T16:20:19Z",
                              "featured": true,
                              "curated": true,
                              "score": 192.49863
                            },
                            {
                              "name": "homebrew",
                              "display_name": "Homebrew",
                              "short_description": "Homebrew is a package manager for macOS.",
                              "description": "Homebrew is a package manager for Apple's macOS operating system. It simplifies the installation of software and is popular in the Ruby on Rails community.",
                              "created_by": "Max Howell",
                              "released": "2009",
                              "created_at": "2016-12-17T20:30:44Z",
                              "updated_at": "2018-02-06T16:14:56Z",
                              "featured": true,
                              "curated": true,
                              "score": 19.194168
                            }
                          ]
                        }
                    """.trimIndent()

            assertEquals(repositories, parser.parseRepositories(json))
        }
    }
})

