package com.github.android.view

import com.github.android.GitHubDependencyManager

interface GitHubViewDependencyManager: GitHubDependencyManager {

    fun getNavigator(): Navigator
}
