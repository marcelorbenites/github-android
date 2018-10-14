package com.github.presentation.list

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.domain.repository.Repositories
import com.github.domain.repository.Repository
import com.github.domain.repository.RepositoryListener
import com.github.domain.repository.RepositoryManager
import com.github.presentation.GitHubViewContainer
import com.github.presentation.Navigator
import com.github.presentation.R

class RepositoryListFragment : Fragment() {

    private var repositoryManager: RepositoryManager? = null
    private var navigator: Navigator? = null

    private var container: GitHubViewContainer? = null
    private var list: RecyclerView? = null
    private var adapter: RepositoryListAdapter? = null
    private var loadingText: TextView? = null
    private var errorText: TextView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is GitHubViewContainer) {
            this.container = context
        } else {
            throw IllegalStateException("Context must implement ${GitHubViewContainer::class.java.simpleName}")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repository_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repositoryManager = container!!.getRepositoryManager()
        navigator = container!!.getNavigator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById(R.id.fragment_repository_list)
        loadingText = view.findViewById(R.id.fragment_repository_list_loading_text)
        errorText = view.findViewById(R.id.fragment_repository_list_error_text)
        adapter = RepositoryListAdapter(mutableListOf(), LayoutInflater.from(context))
        list!!.adapter = adapter
        list!!.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()

        adapter!!.registerListener(object : RepositorySelectionListener {
            override fun onRepositorySelected(repository: Repository) {
                repositoryManager!!.selectRepository(repository.id)
            }
        })

        repositoryManager!!.registerListener(object : RepositoryListener {
            override fun onRepositoriesUpdate(repositories: Repositories) {

                when (repositories.status) {
                    Repositories.Status.IDLE,
                    Repositories.Status.LOADING -> {
                        list!!.visibility = View.GONE
                        loadingText!!.visibility = View.VISIBLE
                        errorText!!.visibility = View.GONE
                    }
                    Repositories.Status.LOADED -> {

                        if (repositories.selectedRepository != null) {
                            navigator!!.navigateToRepositoryDetailView()
                            return
                        }

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
        repositoryManager!!.clearListener()
        adapter!!.clearListener()
        super.onPause()
    }

    override fun onDestroyView() {
        list = null
        adapter = null
        loadingText = null
        errorText = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        repositoryManager = null
        navigator = null
        super.onDestroy()
    }

    override fun onDetach() {
        container = null
        super.onDetach()
    }
}
