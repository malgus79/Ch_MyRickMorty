package com.myrickmorty.ui

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.myrickmorty.R
import com.myrickmorty.databinding.ActivityMainBinding
import com.myrickmorty.ui.fragment.FragmentList
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.popBackStack()

    }

//    override fun onBackPressed() {
//        //super.onBackPressed()
//        val startMain = Intent(Intent.ACTION_MAIN)
//        startMain.addCategory(Intent.CATEGORY_HOME)
//        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(startMain)
//        alertSignOut()
//    }

//    private var exit = false
//    override fun onBackPressed() {
//        if (exit) {
//            super.onBackPressed()
//            return
//        } else {
//            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show()
//            exit = true
//            Handler().postDelayed(Runnable { exit = false }, 2000)
//        }
//    }


    override fun onBackPressed() {
        val count = fragmentManager.backStackEntryCount
        if (count == 0) {
            //super.onBackPressed()
            alertSignOut()
        } else {
            fragmentManager.popBackStack()
        }
    }

//    override fun onBackPressed() {
//        if (binding.navHostFragment.isVisible) {
//            alertSignOut()
//        } else
//            super.onBackPressed()
//    }

//    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
//        return super.getOnBackInvokedDispatcher()
//    }

    private fun alertSignOut() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alertSignOutTitle))
            .setMessage(R.string.alertSignOutTDescription)
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    finish()
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                })
            .setCancelable(true)
            .show()
    }
}