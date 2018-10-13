package com.github.android.repository

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class GitHub(
    private val repositoryGateway: RepositoryGateway,
    private val scheduler: Scheduler,
    private val publishScheduler: Scheduler,
    private val currentRepositories: MutableList<Repository>,
    private var currentPage: Int,
    private var itemsPerPage: Int
) : RepositoryManager {

    private var listener: RepositoryListener? = null
    private var disposable: Disposable? = null

    override fun loadTrendingRepositories() {
        disposable = Single.fromCallable {
            repositoryGateway.getRepositories("star", "desc", currentPage, itemsPerPage)
        }.subscribeOn(scheduler)
            .observeOn(publishScheduler).subscribe { repositories -> updateRepositories(repositories) }
    }

    override fun registerListener(listener: RepositoryListener) {
        this.listener = listener
    }

    override fun clearListener() {
        this.listener = null
    }

    private fun updateRepositories(repositories: List<Repository>) {
        currentRepositories.clear()
        currentRepositories.addAll(repositories)
        listener?.onRepositoryUpdate(currentRepositories)
    }
}