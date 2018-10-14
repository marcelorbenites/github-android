package com.github.android.view.detail

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.android.R
import com.github.android.repository.Repositories
import com.github.android.repository.RepositoryListener
import com.github.android.repository.RepositoryManager
import com.github.android.view.GitHubViewDependencyManager

class RepositoryDetailFragment : Fragment() {

    private var repositoryManager: RepositoryManager? = null
    private var dependencyManager: GitHubViewDependencyManager? = null

    private var name: TextView? = null
    private var description: TextView? = null
    private var author: TextView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is GitHubViewDependencyManager) {
            dependencyManager = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repository_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repositoryManager = dependencyManager!!.getRepositoryManager()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.fragment_repository_detail_name)
        description = view.findViewById(R.id.fragment_repository_detail_description)
        author = view.findViewById(R.id.fragment_repository_detail_author)
    }

    override fun onResume() {
        super.onResume()
        repositoryManager!!.registerListener(object : RepositoryListener {
            override fun onRepositoriesUpdate(repositories: Repositories) {
                if (repositories.selectedRepository != null) {
                    name!!.text = repositories.selectedRepository.name
                    description!!.text = repositories.selectedRepository.description
                    author!!.text = repositories.selectedRepository.author
                }
            }
        })
    }

    override fun onPause() {
        repositoryManager!!.clearListener()
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
        super.onDestroy()
    }

    override fun onDetach() {
        dependencyManager = null
        super.onDetach()
    }
}
