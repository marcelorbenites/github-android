package com.github.presentation

import com.github.domain.repository.RepositoryManager

interface GitHubViewContainer {

    fun getRepositoryManager(): RepositoryManager

    fun getNavigator(): Navigator
}
