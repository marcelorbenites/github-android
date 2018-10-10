package com.github.android

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class GitHub(
    private val repositoryGateway: RepositoryGateway,
    private val scheduler: Scheduler,
    private val publishScheduler: Scheduler,
    private val currentRepositories: MutableList<Repository>
) {

    private var listener: RepositoryListener? = null
    private var disposable: Disposable? = null

    fun loadTrendingRepositories() {
        disposable = Single.fromCallable {
            repositoryGateway.getRepositories()
        }.subscribeOn(scheduler)
            .observeOn(publishScheduler).subscribe { repositories -> updateRepositories(repositories) }
    }

    private fun updateRepositories(repositories: List<Repository>) {
        currentRepositories.clear()
        currentRepositories.addAll(repositories)
        listener?.onRepositoryUpdate(currentRepositories)
    }

    fun registerListener(listener: RepositoryListener) {
        this.listener = listener
    }
}
