package com.github.android

import android.app.Application

class TestApplication: Application(), GitHubDependencyManager {

    var repositoryManager: FakeRepositoryManager? = null

    override fun getRepositoryManager(): RepositoryManager {
        return repositoryManager!!
    }
}
