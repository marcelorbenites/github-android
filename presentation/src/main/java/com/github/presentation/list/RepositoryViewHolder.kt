package com.github.presentation.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.domain.repository.Repository
import com.github.presentation.R

class RepositoryViewHolder(view: View, private val listener: RepositorySelectionListener): RecyclerView.ViewHolder(view) {

    private lateinit var repository: Repository
    private val name = itemView.findViewById<TextView>(R.id.item_github_repository_name)
    private val description = itemView.findViewById<TextView>(R.id.item_github_repository_description)
    private val author = itemView.findViewById<TextView>(R.id.item_github_repository_author)

    init {
        itemView.setOnClickListener {
            listener.onRepositorySelected(repository)
        }
    }

    fun updateRepository(repository: Repository) {
        this.repository = repository
        name.text = repository.name
        description.text = repository.description
        author.text = repository.author
    }
}
