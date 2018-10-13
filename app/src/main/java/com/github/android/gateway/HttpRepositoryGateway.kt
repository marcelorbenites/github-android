package com.github.android.gateway

import com.github.android.repository.Repository
import com.github.android.repository.RepositoryGateway
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpRepositoryGateway private constructor(
    private val baseUrl: String,
    private val httpClient: OkHttpClient,
    private val jsonParser: JsonParser
) : RepositoryGateway {

    override fun getRepositories(sort: String, order: String, page: Int, itemsPerPage: Int): List<Repository> {
        val request = Request.Builder()
            .url("${baseUrl}search/repositories?sort=$sort&order=$order&page=$page&per_page=$itemsPerPage")
            .build()
        val response = httpClient.newCall(request).execute()

        return jsonParser.parseRepositories(response.body()!!.string())
    }

    class Builder {

        private var baseUrl: String? = null
        private var jsonParser: JsonParser? = null
        private var username: String? = null
        private var password: String? = null

        fun setBaseUrl(baseUrl: String?): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun setJsonParser(jsonConverter: JsonParser?): Builder {
            this.jsonParser = jsonConverter
            return this
        }

        fun setUsername(username: String?): Builder {
            this.username = username
            return this
        }

        fun setPassword(password: String?): Builder {
            this.password = password
            return this
        }

        fun build(): HttpRepositoryGateway {

            if (baseUrl == null) {
                throw IllegalStateException("Missing base URL.")
            }

            if (jsonParser == null) {
                throw IllegalStateException("Missing JSON parser.")
            }

            if (username == null) {
                throw IllegalStateException("Missing username.")
            }

            if (password == null) {
                throw IllegalStateException("Missing password.")
            }

            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(chain.request()
                        .newBuilder()
                        .addHeader("Authorization", Credentials.basic(username!!, password!!))
                        .build())
                }
                .build()

            return HttpRepositoryGateway(baseUrl!!, client, jsonParser!!)
        }
    }
}
