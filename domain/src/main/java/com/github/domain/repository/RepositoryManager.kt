package com.github.domain.repository

interface RepositoryManager {
    fun loadTrendingRepositories()
    fun registerListener(listener: RepositoryListener)
    fun clearListener()
    fun selectRepository(repositoryId: String)
    fun clearSelectedRepository()
}
