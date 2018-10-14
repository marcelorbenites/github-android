package com.github.android.view.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.android.R
import com.github.android.repository.Repository

class RepositoryListAdapter(
    private val repositories: MutableList<Repository>,
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<RepositoryViewHolder>() {

    private var delegateListener: RepositorySelectionListener? = null

    private val listener: RepositorySelectionListener by lazy {
        object : RepositorySelectionListener {
            override fun onRepositorySelected(repository: Repository) {
                delegateListener?.onRepositorySelected(repository)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(inflater.inflate(R.layout.item_github_repository, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.updateRepository(repositories[position])
    }

    fun updateRepositories(repositories: List<Repository>) {
        this.repositories.clear()
        this.repositories.addAll(repositories)
        notifyDataSetChanged()
    }

    fun registerListener(listener: RepositorySelectionListener) {
        this.delegateListener = listener
    }

    fun clearListener() {
        this.delegateListener = null
    }
}
