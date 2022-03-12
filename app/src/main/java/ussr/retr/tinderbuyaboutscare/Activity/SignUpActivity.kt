package ussr.retr.tinderbuyaboutscare.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ussr.retr.tinderbuyaboutscare.R
import ussr.retr.tinderbuyaboutscare.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.signUp.setOnClickListener {
            val intent = Intent(this@SignUpActivity, RegistrationActivity::class.java)
            startActivity(intent)
            this@SignUpActivity.finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@SignUpActivity, FirstScreenActivity::class.java)
        startActivity(intent)
    }
}