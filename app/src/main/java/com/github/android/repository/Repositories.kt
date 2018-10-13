package com.github.android.repository

data class Repositories(val list: List<Repository> = emptyList(), val status: Status = Status.IDLE) {

    enum class Status {
        IDLE,
        LOADING,
        LOADED,
        ERROR
    }
}
