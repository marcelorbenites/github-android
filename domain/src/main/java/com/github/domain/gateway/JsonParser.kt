package com.github.domain.gateway

import com.github.domain.repository.Repository

interface JsonParser {

    fun parseRepositories(json: String): List<Repository>
}
