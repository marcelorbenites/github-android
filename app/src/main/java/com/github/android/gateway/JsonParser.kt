package com.github.android.gateway

import com.github.android.repository.Repository

interface JsonParser {

    fun parseRepositories(json: String): List<Repository>
}
