package com.testexample.componentExample.view.fragment


import android.app.SearchManager
import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager

import com.testexample.componentExample.R
import com.testexample.componentExample.common.Constants
import com.testexample.componentExample.data.local.entity.ArticleEntity
import com.testexample.componentExample.data.remote.Resource
import com.testexample.componentExample.data.remote.Status
import com.testexample.componentExample.databinding.FragmentListArticlesBinding
import com.testexample.componentExample.utils.FragmentUtils
import com.testexample.componentExample.view.adapter.ArticleListAdapter
import com.testexample.componentExample.view.base.BaseFragment
import com.testexample.componentExample.view.callbacks.ArticleListCallback
import com.testexample.componentExample.viewmodel.ArticleListViewModel




/**
 * The article list fragment which will list the popular articles
 */
class ArticleListFragment : BaseFragment<ArticleListViewModel, FragmentListArticlesBinding>(), ArticleListCallback {
    public override val layoutRes: Int = R.layout.fragment_list_articles
    //To change initializer of created properties use File | Settings | File Templates.

    public override fun getViewModel(): Class<ArticleListViewModel> {
        return ArticleListViewModel::class.java
    }

    override fun onArticleClicked(articleEntity: ArticleEntity) {
        if (null != activity) {
            val args = Bundle()
            args.putString(Constants.BUNDLE_KEY_ARTICLE_URL, articleEntity.link)
            args.putString(Constants.BUNDLE_KEY_ARTICLE_PUBLISHED_DATE, articleEntity.body)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)
        dataBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        dataBinding.recyclerView.setAdapter(ArticleListAdapter(this))
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.popularArticles
                .observe(this, Observer<Resource<List<ArticleEntity>>>{ listResource ->
                    run {
                        if (null != listResource && (listResource!!.status == Status.ERROR || listResource!!.status == Status.SUCCESS)) {
                            dataBinding.loginProgress.setVisibility(View.GONE)
                        }
                        dataBinding.resource = listResource

                        // If the cached data is already showing then no need to show the error
                        if (null != dataBinding.recyclerView.getAdapter() && dataBinding.recyclerView.getAdapter()!!.getItemCount() > 0) {
                            dataBinding.errorText.visibility = View.GONE
                        }
                    }
                })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if (null == activity)
            return

        val searchView: SearchView?
        activity!!.menuInflater.inflate(R.menu.menu_main, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.action_search)
                .actionView as? SearchView

        searchView!!.setSearchableInfo(searchManager
                .getSearchableInfo(activity!!.componentName))
        searchView!!.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                if (null != dataBinding.recyclerView.getAdapter())
                    (dataBinding.recyclerView.getAdapter() as ArticleListAdapter).filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                if (null != dataBinding.recyclerView.getAdapter())
                    (dataBinding.recyclerView.getAdapter() as ArticleListAdapter).filter.filter(query)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item!!.itemId


        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)

    }

    companion object {

        fun newInstance(): ArticleListFragment {
            val args = Bundle()
            val fragment = ArticleListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
