package com.github.domain.repository

interface RepositoryListener {
    fun onRepositoriesUpdate(repositories: Repositories)
}
