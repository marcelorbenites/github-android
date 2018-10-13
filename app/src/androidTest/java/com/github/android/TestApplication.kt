package com.github.android

import android.app.Application
import com.github.android.repository.FakeRepositoryManager
import com.github.android.repository.RepositoryManager

class TestApplication: Application(), GitHubDependencyManager {

    var repositoryManager: FakeRepositoryManager? = null

    override fun getRepositoryManager(): RepositoryManager {
        return repositoryManager!!
    }
}
