package com.github.android

interface RepositoryGateway {
    fun getRepositories(sort: String, order: String, page: Int, itemsPerPage: Int): List<Repository>
}
