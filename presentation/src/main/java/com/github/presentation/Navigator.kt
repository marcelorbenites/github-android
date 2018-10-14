package com.github.presentation

interface Navigator {

    fun navigateToRepositoryDetailView()
    fun registerBackNavigationListener(listener: BackNavigationListener)
    fun clearBackNavigationListener()
    fun navigateBack()
}
