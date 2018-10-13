package com.github.android.repository

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class GitHub(
    private val repositoryGateway: RepositoryGateway,
    private val scheduler: Scheduler,
    private val publishScheduler: Scheduler,
    private var currentPage: Int,
    private var itemsPerPage: Int,
    private var currentRepositories: Repositories
) : RepositoryManager {

    private var listener: RepositoryListener? = null
    private var disposable: Disposable? = null

    override fun loadTrendingRepositories() {
        updateRepositories(Repositories(emptyList(), Repositories.Status.LOADING))
        disposable = Single.fromCallable {
            repositoryGateway.getRepositories("star", "desc", currentPage, itemsPerPage)
        }
            .subscribeOn(scheduler)
            .observeOn(publishScheduler).subscribe(
                { repositories ->
                    updateRepositories(Repositories(repositories, Repositories.Status.LOADED))
                },
                { updateRepositories(Repositories(emptyList(), Repositories.Status.ERROR)) }
            )
    }

    override fun registerListener(listener: RepositoryListener) {
        this.listener = listener
        this.listener?.onRepositoriesUpdate(currentRepositories)
    }

    override fun clearListener() {
        this.listener = null
    }

    private fun updateRepositories(repositories: Repositories) {
        currentRepositories = repositories
        listener?.onRepositoriesUpdate(currentRepositories)
    }
}
