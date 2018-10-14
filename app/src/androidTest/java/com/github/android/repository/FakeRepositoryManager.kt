package com.github.android.repository

class FakeRepositoryManager(private val repositories: Repositories, private val selectedRepository: Repository? = null) : RepositoryManager {

    private var listener: RepositoryListener? = null

    override fun registerListener(listener: RepositoryListener) {
        this.listener = listener
        listener.onRepositoriesUpdate(repositories)
    }

    override fun selectRepository(repositoryId: String) {
        listener?.onRepositoriesUpdate(repositories.copy(selectedRepository = selectedRepository))
    }

    override fun clearListener() {
        this.listener = null
    }

    override fun loadTrendingRepositories() {
        listener?.onRepositoriesUpdate(repositories)
    }
}
