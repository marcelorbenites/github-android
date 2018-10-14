package com.github.domain.repository

interface RepositoryGateway {
    fun getRepositories(sort: String, order: String, page: Int, itemsPerPage: Int): List<Repository>
}
