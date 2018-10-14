package com.github.android.view.list

import com.github.android.repository.Repository

interface RepositorySelectionListener {
    fun onRepositorySelected(repository: Repository)
}
