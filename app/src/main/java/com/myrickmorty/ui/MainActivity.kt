package com.myrickmorty.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.myrickmorty.R
import com.myrickmorty.core.ConnectionLiveData
import com.myrickmorty.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var cld: ConnectionLiveData
    private var booleanState: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkNetworkConnection()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    private fun checkNetworkConnection() {
        cld = ConnectionLiveData(application)
        cld.observe(this) { isConnected ->

            if (booleanState == null && isConnected) {
                booleanState = false
            }

            if (booleanState == false && !isConnected) {
                Snackbar.make(binding.root, "Sin conexión a internet", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Descartar") {}
                    .show()
                booleanState = true
            }

            if (booleanState == true && isConnected) {
                Snackbar.make(binding.root, "Conectado a internet", Snackbar.LENGTH_SHORT).show()
                booleanState = false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.popBackStack()

    }
}