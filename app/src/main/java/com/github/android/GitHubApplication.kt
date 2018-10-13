package com.github.android

import android.app.Application
import com.github.android.repository.GitHub
import com.github.android.repository.RepositoryManager
import com.github.android.gateway.HttpRepositoryGateway
import com.github.android.gateway.parser.JsonObjectParser
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
            mutableListOf(),
            BuildConfig.GITHUB_TRENDING_REPOSTORIES_START_PAGE,
            BuildConfig.GITHUB_TRENDING_REPOSTORIES_ITEMS_PER_PAGE
        )
        gitHub.loadTrendingRepositories()
    }

    override fun getRepositoryManager(): RepositoryManager {
        return gitHub
    }
}
