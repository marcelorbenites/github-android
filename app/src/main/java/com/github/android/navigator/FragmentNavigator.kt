package com.github.android.navigator

import android.support.v4.app.FragmentManager
import com.github.android.R
import com.github.android.view.Navigator
import com.github.android.view.detail.RepositoryDetailFragment
import com.github.android.view.list.RepositoryListFragment

class FragmentNavigator(private val fragmentManager: FragmentManager) : Navigator {

    override fun navigateToRepositoryDetailView() {
        fragmentManager
            .beginTransaction()
            .replace(R.id.activity_github_container, RepositoryDetailFragment())
            .addToBackStack("RepositoryList")
            .commit()
    }

    override fun navigateToRepositoryListView() {
        fragmentManager
            .beginTransaction()
            .replace(R.id.activity_github_container, RepositoryListFragment())
            .commit()
    }
}
