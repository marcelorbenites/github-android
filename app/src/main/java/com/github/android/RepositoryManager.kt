package com.github.android

interface RepositoryManager {
    fun loadTrendingRepositories()
    fun registerListener(listener: RepositoryListener)
    fun clearListener()
}
