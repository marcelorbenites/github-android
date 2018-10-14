package com.github.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.github.domain.repository.RepositoryManager
import com.github.presentation.BackNavigationListener
import com.github.presentation.GitHubViewContainer
import com.github.presentation.Navigator
import com.github.presentation.detail.RepositoryDetailFragment
import com.github.presentation.list.RepositoryListFragment

class GitHubActivity : AppCompatActivity(), GitHubViewContainer, Navigator {

    private var listener: BackNavigationListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_github_container, RepositoryListFragment())
                .commit()
        }
    }

    override fun onBackPressed() {
        if (listener != null) {
            listener?.onBackNavigation()
        } else {
            super.onBackPressed()
        }
    }

    override fun getRepositoryManager(): RepositoryManager {
        return (application as GitHubDependencyManager).getRepositoryManager()
    }

    override fun getNavigator(): Navigator {
        return this
    }

    override fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    override fun navigateToRepositoryDetailView() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_github_container, RepositoryDetailFragment())
            .addToBackStack("RepositoryList")
            .commit()
    }

    override fun navigateBack() {
        supportFragmentManager.popBackStackImmediate()
    }

    override fun registerBackNavigationListener(listener: BackNavigationListener) {
        this.listener = listener
    }

    override fun clearBackNavigationListener() {
        this.listener = null
    }
}
