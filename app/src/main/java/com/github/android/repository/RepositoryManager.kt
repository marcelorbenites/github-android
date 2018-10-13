package com.github.android.repository

interface RepositoryManager {
    fun loadTrendingRepositories()
    fun registerListener(listener: RepositoryListener)
    fun clearListener()
}
