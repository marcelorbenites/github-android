package com.github.presentation

open class FakeNavigator : Navigator {

    private var listener: BackNavigationListener? = null

    override fun navigateToRepositoryDetailView() {
    }

    override fun registerBackNavigationListener(listener: BackNavigationListener) {
        this.listener = listener
    }

    override fun clearBackNavigationListener() {
        this.listener = null
    }

    override fun navigateBack() {
    }

    fun simulateBackPressed() {
        listener?.onBackNavigation()
    }
}
