package com.github.android.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.android.GitHubDependencyManager
import com.github.android.R
import com.github.android.navigator.FragmentNavigator
import com.github.android.repository.RepositoryManager

class GitHubActivity : AppCompatActivity(), GitHubViewDependencyManager {

    private lateinit var navigator: FragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github)

        navigator = FragmentNavigator(supportFragmentManager)

        if (savedInstanceState == null) {
            navigator.navigateToRepositoryListView()
        }
    }

    override fun getRepositoryManager(): RepositoryManager {
        return (application as GitHubDependencyManager).getRepositoryManager()
    }

    override fun getNavigator(): Navigator {
        return navigator
    }
}
