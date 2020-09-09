/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationControl: NavController

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navigationControl = this.findNavController(R.id.myNavHostFragment)
        drawerLayout = binding.drawerLayout
        appBarConfiguration = AppBarConfiguration(navigationControl.graph, drawerLayout)

        // 初始化 View
        initView()
        // 初始化 Event
        initEvent()
    }

    /** 初始化 Event */
    private fun initEvent() {

        // 判斷是否可以右滑顯示滑動抽屜
        navigationControl.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if(nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    /** 初始化 View */
    private fun initView() {

        NavigationUI.setupActionBarWithNavController(this, navigationControl, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navigationControl)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationControl, drawerLayout)
    }
}
