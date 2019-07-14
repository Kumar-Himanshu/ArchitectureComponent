package com.testexample.componentExample.view.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.testexample.componentExample.R

import com.testexample.componentExample.databinding.ItemArticleListBinding
import com.testexample.componentExample.view.base.BaseAdapter
import com.testexample.componentExample.view.callbacks.ArticleListCallback

import java.util.ArrayList
import com.testexample.componentExample.data.local.entity.ArticleEntity
import io.reactivex.annotations.NonNull


/**
 * This class represents the Article list recyclerview adapter
 */
class ArticleListAdapter(private val articleListCallback: ArticleListCallback) : BaseAdapter<ArticleListAdapter.ArticleViewHolder,ArticleEntity>(), Filterable {

    private var articleEntities: List<ArticleEntity>? = null

    private var articleEntitiesFiltered: List<ArticleEntity>? = null

    init {
        articleEntities = ArrayList()
        articleEntitiesFiltered = ArrayList()
    }

    override fun setData(data: List<ArticleEntity>) {
        this.articleEntities = data
        this.articleEntitiesFiltered = data
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): ArticleViewHolder {
        val binding: ItemArticleListBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_article_list, viewGroup, false)
        return ArticleViewHolder(binding,articleListCallback).create(LayoutInflater.from(viewGroup.context), viewGroup, articleListCallback)
    }

    override fun onBindViewHolder(viewHolder: ArticleViewHolder, i: Int) {
        viewHolder.onBind(articleEntitiesFiltered!![i])
    }

    override fun getItemCount(): Int {
        return articleEntitiesFiltered!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    articleEntitiesFiltered = articleEntities
                } else {
                    val filteredList = ArrayList<ArticleEntity>()
                    for (row in articleEntities!!) {

                        // name match condition. this might differ depending on your requirement
                        if (row.title!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }

                    articleEntitiesFiltered = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = articleEntitiesFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                articleEntitiesFiltered = filterResults.values as? ArrayList<ArticleEntity>
                notifyDataSetChanged()
            }
        }
    }



    inner class ArticleViewHolder(val binding: ItemArticleListBinding, callback: ArticleListCallback) : RecyclerView.ViewHolder(binding.root) {

        public fun create(inflater: LayoutInflater, parent: ViewGroup, callback: ArticleListCallback): ArticleViewHolder {
            val itemMovieListBinding = ItemArticleListBinding.inflate(inflater, parent, false)
            return ArticleViewHolder(itemMovieListBinding, callback)
        }

        init {
            binding.root.setOnClickListener { v -> callback.onArticleClicked(binding.article!!) }
        }

        fun onBind(articleEntity: ArticleEntity) {
            binding.article = articleEntity
            binding.executePendingBindings()
        }
    }
}
