package com.myrickmorty.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.myrickmorty.R
import com.myrickmorty.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel: MainActivityViewModel by viewModels()

    private var booleanState: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        checkNetworkConnection()

    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        NavigationUI.setupActionBarWithNavController(this, navController)

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }

    private fun checkNetworkConnection() {
        lifecycleScope.launchWhenCreated {
            viewModel.isConnected.collectLatest { isConnected ->

                if (booleanState == null && isConnected) {
                    booleanState = false
                }

                if (booleanState == false && !isConnected) {
                    snackBarConnectivityOff()
                    booleanState = true
                }

                if (booleanState == true && isConnected) {
                    snackBarConnectivityOn()
                    booleanState = false
                }
            }
        }
    }

    private fun snackBarConnectivityOff() {
        Snackbar.make(binding.root, R.string.no_connection, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.rule_out)) {}
            .setAnchorView(binding.bottomNavigationView)
            .setBackgroundTint(
                ContextCompat.getColor(this@MainActivity, R.color.red_theme))
            .show()
    }

    private fun snackBarConnectivityOn() {
        Snackbar.make(binding.root, R.string.connection_restored, Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.bottomNavigationView)
            .setBackgroundTint(
                ContextCompat.getColor(this@MainActivity, R.color.green_dark_theme)
            )
            .show()
    }
}