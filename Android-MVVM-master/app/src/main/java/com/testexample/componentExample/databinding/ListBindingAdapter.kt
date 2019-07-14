package com.testexample.componentExample.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

import com.testexample.componentExample.data.remote.Resource
import com.testexample.componentExample.view.base.BaseAdapter

/**
 * Binding adapters
 */
object ListBindingAdapter {

    @BindingAdapter(value = ["resource"])
    @JvmStatic
    fun setResource(recyclerView: RecyclerView, resource: Resource<*>?) {
        val adapter = recyclerView.adapter ?: return

        if (resource == null || resource.data == null)
            return

        if (adapter is BaseAdapter<*, *>) {
            adapter.setData((resource.data as List<Nothing>?)!!)
        }
    }

}// Private Constructor to hide the implicit one
