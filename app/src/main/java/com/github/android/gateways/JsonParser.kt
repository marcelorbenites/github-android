package com.github.android.gateways

import com.github.android.Repository

interface JsonParser {

    fun parseRepositories(json: String): List<Repository>
}
