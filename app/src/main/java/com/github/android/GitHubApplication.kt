package com.github.android

import android.app.Application
import com.github.domain.gateway.HttpRepositoryGateway
import com.github.domain.gateway.parser.JsonObjectParser
import com.github.domain.repository.GitHub
import com.github.domain.repository.Repositories
import com.github.domain.repository.RepositoryManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GitHubApplication: Application(), GitHubDependencyManager {

    private lateinit var gitHub: GitHub

    override fun onCreate() {
        super.onCreate()
        gitHub = GitHub(
            HttpRepositoryGateway.Builder()
                .setBaseUrl(BuildConfig.GITHUB_API_BASE_URL)
                .setJsonParser(JsonObjectParser())
                .setUsername(BuildConfig.GITHUB_USERNAME)
                .setPassword(BuildConfig.GITHUB_PASSWORD)
                .build(),
            Schedulers.io(),
            AndroidSchedulers.mainThread(),
            BuildConfig.GITHUB_TRENDING_REPOSTORIES_START_PAGE,
            BuildConfig.GITHUB_TRENDING_REPOSTORIES_ITEMS_PER_PAGE,
            Repositories()
        )
        gitHub.loadTrendingRepositories()
    }

    override fun getRepositoryManager(): RepositoryManager {
        return gitHub
    }
}
