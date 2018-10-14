package com.github.presentation

import android.app.Application
import com.github.domain.repository.RepositoryManager

class TestApplication : Application() {

    var repositoryManager: FakeRepositoryManager? = null

    fun getRepositoryManager(): RepositoryManager {
        return repositoryManager!!
    }
}
