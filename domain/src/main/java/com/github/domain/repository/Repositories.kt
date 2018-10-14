package com.github.domain.repository

data class Repositories(
    val list: List<Repository> = emptyList(),
    val status: Status = Status.IDLE,
    val selectedRepository: Repository? = null
) {

    enum class Status {
        IDLE,
        LOADING,
        LOADED,
        ERROR
    }
}
