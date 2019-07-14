package com.testexample.componentExample.view.activity

import android.os.Bundle
import android.view.MenuItem

import com.testexample.componentExample.R
import com.testexample.componentExample.utils.FragmentUtils
import com.testexample.componentExample.utils.FragmentUtils.TRANSITION_NONE
import com.testexample.componentExample.view.base.BaseActivity
import com.testexample.componentExample.view.fragment.ArticleListFragment

class MainActivity : BaseActivity<com.testexample.componentExample.databinding.ActivityMainBinding>() {

    public override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentUtils.replaceFragment(this, ArticleListFragment.newInstance(), R.id.fragContainer, false, TRANSITION_NONE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }
}
