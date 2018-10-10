package com.github.android.gateways.parser

import com.github.android.Repository
import com.github.android.gateways.JsonParser
import org.json.JSONObject

class JsonObjectParser : JsonParser {

    override fun parseRepositories(json: String): List<Repository> {

        val jsonObject = JSONObject(json)
        val items = jsonObject.getJSONArray("items")
        val repositories = mutableListOf<Repository>()

        for (position in 0 until items.length()) {
            repositories.add(parseRepository(items.getJSONObject(position)))
        }

        return repositories
    }

    private fun parseRepository(jsonObject: JSONObject): Repository {
        return Repository(
            jsonObject.getString("display_name"),
            jsonObject.getString("short_description"),
            jsonObject.getString("created_by")
        )
    }
}
