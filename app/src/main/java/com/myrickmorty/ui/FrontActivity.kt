package com.myrickmorty.ui

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.myrickmorty.R
import com.myrickmorty.databinding.ActivityFrontBinding

class FrontActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFrontBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrontBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAnim()
        setupActionButtons()

    }

    private fun setupActionButtons() {
        binding.btnEnter.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnOut.setOnClickListener {
            finish()
        }
    }

    private fun setupAnim() {
        //This is used to hide the status bar and make the splash screen as a full screen activity
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo)
        binding.layoutBtn.startAnimation(bottomAnimation)

        val headAnimation = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba)
        binding.ivFrontPage.startAnimation(headAnimation)

    }
}