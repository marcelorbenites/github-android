package com.github.android.view

import com.github.android.repository.Repository

interface RepositorySelectionListener {
    fun onRepositorySelected(repository: Repository)
}
