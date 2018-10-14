package com.github.presentation

import com.github.domain.repository.Repositories
import com.github.domain.repository.Repository
import com.github.domain.repository.RepositoryListener
import com.github.domain.repository.RepositoryManager

open class FakeRepositoryManager(private val repositories: Repositories, private val selectedRepository: Repository? = null) : RepositoryManager {

    private var listener: RepositoryListener? = null

    override fun registerListener(listener: RepositoryListener) {
        this.listener = listener
        listener.onRepositoriesUpdate(repositories)
    }

    override fun selectRepository(repositoryId: String) {
        listener?.onRepositoriesUpdate(repositories.copy(selectedRepository = selectedRepository))
    }

    override fun clearSelectedRepository() {
        listener?.onRepositoriesUpdate(repositories.copy(selectedRepository = null))
    }

    override fun clearListener() {
        this.listener = null
    }

    override fun loadTrendingRepositories() {
    }
}
