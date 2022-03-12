package ussr.retr.tinderbuyaboutscare.Activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.children
import androidx.core.view.doOnLayout
import ussr.retr.tinderbuyaboutscare.databinding.ActivityFirstScreenBinding
import kotlin.random.Random


class FirstScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.materialButton.setOnClickListener {
            val intent = Intent(this@FirstScreenActivity, SignUpActivity::class.java)
            startActivity(intent)
            this@FirstScreenActivity.finish()
        }

        binding.signInButton.setOnClickListener {
            val intent = Intent(this@FirstScreenActivity, SignInActivity::class.java)
            startActivity(intent)
            this@FirstScreenActivity.finish()
        }
    }
}