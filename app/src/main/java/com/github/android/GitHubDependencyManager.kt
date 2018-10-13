package com.github.android

import com.github.android.repository.RepositoryManager

interface GitHubDependencyManager {
    fun getRepositoryManager(): RepositoryManager
}
