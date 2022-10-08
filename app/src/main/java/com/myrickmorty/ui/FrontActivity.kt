package com.myrickmorty.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.myrickmorty.R
import com.myrickmorty.databinding.ActivityFrontBinding

class FrontActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFrontBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrontBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnter.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnOut.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        alertSignOut()
    }

    //Dialog for signout
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