package com.github.android.repository

interface RepositoryListener {
    fun onRepositoriesUpdate(repositories: Repositories)
}
