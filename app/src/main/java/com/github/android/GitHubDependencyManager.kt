package com.github.android

import com.github.domain.repository.RepositoryManager

interface GitHubDependencyManager {
    fun getRepositoryManager(): RepositoryManager
}
