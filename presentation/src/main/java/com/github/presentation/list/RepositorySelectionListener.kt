package com.github.presentation.list

import com.github.domain.repository.Repository

interface RepositorySelectionListener {
    fun onRepositorySelected(repository: Repository)
}
