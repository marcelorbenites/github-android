package com.github.presentation.detail

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.domain.repository.Repositories
import com.github.domain.repository.RepositoryListener
import com.github.domain.repository.RepositoryManager
import com.github.presentation.BackNavigationListener
import com.github.presentation.GitHubViewContainer
import com.github.presentation.Navigator
import com.github.presentation.R

class RepositoryDetailFragment : Fragment() {

    private var repositoryManager: RepositoryManager? = null
    private var container: GitHubViewContainer? = null
    private var navigator: Navigator? = null

    private var name: TextView? = null
    private var description: TextView? = null
    private var author: TextView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is GitHubViewContainer) {
            container = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repository_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repositoryManager = container!!.getRepositoryManager()
        navigator = container!!.getNavigator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.fragment_repository_detail_name)
        description = view.findViewById(R.id.fragment_repository_detail_description)
        author = view.findViewById(R.id.fragment_repository_detail_author)
    }

    override fun onResume() {
        super.onResume()

        navigator!!.registerBackNavigationListener(object : BackNavigationListener {
            override fun onBackNavigation() {
                repositoryManager!!.clearSelectedRepository()
            }
        })

        repositoryManager!!.registerListener(object : RepositoryListener {
            override fun onRepositoriesUpdate(repositories: Repositories) {
                if (repositories.selectedRepository != null) {
                    name!!.text = repositories.selectedRepository!!.name
                    description!!.text = repositories.selectedRepository!!.description
                    author!!.text = repositories.selectedRepository!!.author
                } else {
                    navigator!!.navigateBack()
                }
            }
        })
    }

    override fun onPause() {
        repositoryManager!!.clearListener()
        navigator!!.clearBackNavigationListener()
        super.onPause()
    }

    override fun onDestroyView() {
        name = null
        description = null
        author = null
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
