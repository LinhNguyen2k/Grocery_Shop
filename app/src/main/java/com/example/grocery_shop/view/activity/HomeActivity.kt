package com.example.grocery_shop.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseActivity
import com.example.grocery_shop.databinding.ActivityHomeBinding
import com.example.grocery_shop.util.StatusBarUtil
import com.example.grocery_shop.view.fragment.CustomerFragment
import com.example.grocery_shop.view.fragment.HomeFragment
import com.example.grocery_shop.view.fragment.SellFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private var currentPositionBtmNav = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private fun setUpBottomNav() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorBlack))
        binding.bottomNavigation.itemIconTintList = null


        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_home -> {
                    if (currentPositionBtmNav == 0) return@setOnNavigationItemSelectedListener false
                    currentPositionBtmNav = 0
                    setHome()
                    true
                }
                R.id.tab_sell -> {
                    if (currentPositionBtmNav == 1) return@setOnNavigationItemSelectedListener false
                    currentPositionBtmNav = 1
                    setSellFragment()
                    true
                }
                R.id.tab_customer -> {
                    if (currentPositionBtmNav == 2) return@setOnNavigationItemSelectedListener false
                    currentPositionBtmNav = 2
                    setCustomerFragment()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }


     fun replaceFragment(mFragment: Fragment, addBackStack: Boolean? = false) {
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.mainFrame, mFragment)
        addBackStack?.let { add ->
            if (add) {
                trans.addToBackStack(mFragment.javaClass.name)
            }
        }
        trans.commit()
    }

     fun replaceFragmentFullScreen(mFragment: Fragment, addBackStack: Boolean? = false) {
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.rl_contain_full_screen, mFragment)
        addBackStack?.let { add ->
            if (add) {
                trans.addToBackStack(mFragment.javaClass.name)
            }
        }
        trans.commit()
    }

    private fun setHome() {
        replaceFragment(HomeFragment(), true)
    }


    private fun setSellFragment() {
        replaceFragment(SellFragment())
    }


    private fun setCustomerFragment() {
        replaceFragment(CustomerFragment())
    }


    fun enableBottomBar(enable: Boolean) {
        for (i in 0 until binding.bottomNavigation.menu.size()) {
            binding.bottomNavigation.menu.getItem(i).isEnabled = enable
        }
    }


    override fun onBackPressed() {
        if (currentPositionBtmNav == 0 && supportFragmentManager.backStackEntryCount >= 1) {
            supportFragmentManager.popBackStack()
        } else if (currentPositionBtmNav == 1 && supportFragmentManager.backStackEntryCount >= 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun initView() {
        setHome()
        setUpBottomNav()
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}