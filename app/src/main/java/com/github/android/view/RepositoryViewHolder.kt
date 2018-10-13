package com.github.android.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.android.R
import com.github.android.repository.Repository

class RepositoryViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val name = itemView.findViewById<TextView>(R.id.item_github_repository_name)
    private val description = itemView.findViewById<TextView>(R.id.item_github_repository_description)
    private val author = itemView.findViewById<TextView>(R.id.item_github_repository_author)

    fun updateRepository(repository: Repository) {
        name.text = repository.name
        description.text = repository.description
        author.text = repository.author
    }
}