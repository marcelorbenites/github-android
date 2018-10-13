package com.github.android.repository

import com.github.android.repository.Repository

interface RepositoryListener {
    fun onRepositoryUpdate(repositories: List<Repository>)
}
