package com.github.android.repository

class FakeRepositoryManager(private val repositories: Repositories) : RepositoryManager {

    private var listener: RepositoryListener? = null

    override fun registerListener(listener: RepositoryListener) {
        this.listener = listener
        listener.onRepositoriesUpdate(repositories)
    }

    override fun clearListener() {
        this.listener = null
    }

    override fun loadTrendingRepositories() {
        listener?.onRepositoriesUpdate(repositories)
    }
}
