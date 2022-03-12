package ussr.retr.tinderbuyaboutscare.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import ussr.retr.tinderbuyaboutscare.Fragments.CardsFragment
import ussr.retr.tinderbuyaboutscare.R
import ussr.retr.tinderbuyaboutscare.databinding.ActivityTinderBinding

class TinderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTinderBinding
    var event: MotionEvent? = null
    private lateinit var tinderFragment: CardsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTinderBinding.inflate(layoutInflater)
        tinderFragment = CardsFragment()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setFragment(CardsFragment())
        initBottomNavigation()

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    private fun initBottomNavigation()
    {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.tinderPage -> {
                    setFragment(CardsFragment())
                    true
                }
                else -> {
                    setFragment(
                        tinderFragment
                    )
                    true
                }
            }
        }
    }

    fun setFragment(fragment: Fragment)
    {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}