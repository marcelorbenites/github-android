package com.github.presentation

import android.support.v7.widget.Toolbar
import com.github.domain.repository.RepositoryManager

interface GitHubViewContainer {

    fun getRepositoryManager(): RepositoryManager

    fun getNavigator(): Navigator

    fun setToolbar(toolbar: Toolbar)
}
