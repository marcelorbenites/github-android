package com.github.android.view

import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class ViewMatchersExtension {

    companion object {

        fun withHolderView(viewMatcher: Matcher<View>): Matcher<RecyclerView.ViewHolder> {
            return WithViewHolderViewMatcher(viewMatcher)
        }
    }
}

internal class WithViewHolderViewMatcher(private val viewMatcher: Matcher<View>) : TypeSafeMatcher<RecyclerView.ViewHolder>() {

    override fun describeTo(description: Description) {
        description.appendText("with view holder: ")
        viewMatcher.describeTo(description)
    }

    override fun matchesSafely(item: RecyclerView.ViewHolder): Boolean {
        return viewMatcher.matches(item.itemView)
    }
}
