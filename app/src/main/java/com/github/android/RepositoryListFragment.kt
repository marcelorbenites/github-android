package com.github.android

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RepositoryListFragment : Fragment() {

    private lateinit var repositoryManager: RepositoryManager

    private var container: GitHubDependencyManager? = null
    private var list: RecyclerView? = null
    private var adapter: RepositoryListAdapter? = null

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
        adapter = RepositoryListAdapter(mutableListOf(), LayoutInflater.from(context))
        list!!.adapter = adapter
        list!!.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        repositoryManager.registerListener(object : RepositoryListener {
            override fun onRepositoryUpdate(repositories: List<Repository>) {
                adapter!!.updateRepositories(repositories)
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
        super.onDestroyView()
    }

    override fun onDetach() {
        container = null
        super.onDetach()
    }
}
