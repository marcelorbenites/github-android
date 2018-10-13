package com.github.android

class FakeRepositoryManager(private val repositories: List<Repository>) : RepositoryManager {

    private var listener: RepositoryListener? = null

    override fun registerListener(listener: RepositoryListener) {
        this.listener = listener
        listener.onRepositoryUpdate(repositories)
    }

    override fun clearListener() {
        this.listener = null
    }

    override fun loadTrendingRepositories() {
        listener?.onRepositoryUpdate(repositories)
    }
}
