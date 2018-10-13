package com.github.android.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.android.GitHubDependencyManager
import com.github.android.R
import com.github.android.repository.Repositories
import com.github.android.repository.RepositoryListener
import com.github.android.repository.RepositoryManager

class RepositoryListFragment : Fragment() {

    private lateinit var repositoryManager: RepositoryManager

    private var container: GitHubDependencyManager? = null
    private var list: RecyclerView? = null
    private var adapter: RepositoryListAdapter? = null
    private var loadingText: TextView? = null
    private var errorText: TextView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is GitHubDependencyManager) {
            this.container = context
        } else {
            throw IllegalStateException("Context must implement ${GitHubDependencyManager::class.java.simpleName}")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repository_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repositoryManager = container!!.getRepositoryManager()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById(R.id.fragment_repository_list)
        loadingText = view.findViewById(R.id.fragment_repository_loading_text)
        errorText = view.findViewById(R.id.fragment_repository_error_text)
        adapter = RepositoryListAdapter(mutableListOf(), LayoutInflater.from(context))
        list!!.adapter = adapter
        list!!.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        repositoryManager.registerListener(object : RepositoryListener {
            override fun onRepositoriesUpdate(repositories: Repositories) {

                when (repositories.status) {
                    Repositories.Status.IDLE,
                    Repositories.Status.LOADING -> {
                        list!!.visibility = View.GONE
                        loadingText!!.visibility = View.VISIBLE
                        errorText!!.visibility = View.GONE
                    }
                    Repositories.Status.LOADED -> {
                        adapter!!.updateRepositories(repositories.list)
                        list!!.visibility = View.VISIBLE
                        loadingText!!.visibility = View.GONE
                        errorText!!.visibility = View.GONE
                    }
                    Repositories.Status.ERROR -> {
                        list!!.visibility = View.GONE
                        loadingText!!.visibility = View.GONE
                        errorText!!.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onPause() {
        repositoryManager.clearListener()
        super.onPause()
    }

    override fun onDestroyView() {
        list = null
        adapter = null
        loadingText = null
        errorText = null
        super.onDestroyView()
    }

    override fun onDetach() {
        container = null
        super.onDetach()
    }
}
