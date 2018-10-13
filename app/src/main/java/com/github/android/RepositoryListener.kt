package com.github.android

interface RepositoryListener {
    fun onRepositoryUpdate(repositories: List<Repository>)
}
