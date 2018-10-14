package com.github.android

import com.github.android.view.Navigator

interface GitHubViewDependencyManager: GitHubDependencyManager {

    fun getNavigator(): Navigator
}
