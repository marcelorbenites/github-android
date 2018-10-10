package com.github.android

class FakeRepositoryGateway(private val repositories: List<Repository>) : RepositoryGateway {

    override fun getRepositories(): List<Repository> {
        return repositories
    }
}
