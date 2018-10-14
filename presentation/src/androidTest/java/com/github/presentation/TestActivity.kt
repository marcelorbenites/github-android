package com.github.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.github.domain.repository.RepositoryManager
import com.github.presentation.test.R

class TestActivity : AppCompatActivity(), GitHubViewContainer {

    var testNavigator: Navigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    override fun getRepositoryManager(): RepositoryManager {
        return (application as TestApplication).getRepositoryManager()
    }

    override fun getNavigator(): Navigator {
        return testNavigator!!
    }

    override fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_test_container, fragment)
            .commit()
    }
}

