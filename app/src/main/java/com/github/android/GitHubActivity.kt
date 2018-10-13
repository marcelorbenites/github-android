package com.github.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class GitHubActivity : AppCompatActivity(), GitHubDependencyManager {

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

    override fun getRepositoryManager(): RepositoryManager {
        return (application as GitHubDependencyManager).getRepositoryManager()
    }
}
