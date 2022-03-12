package ussr.retr.tinderbuyaboutscare.Fragments

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ussr.retr.tinderbuyaboutscare.Objects.TinderCard
import ussr.retr.tinderbuyaboutscare.R
import ussr.retr.tinderbuyaboutscare.databinding.FragmentBlankBinding
import ussr.retr.tinderbuyaboutscare.databinding.FragmentCardsBinding

class BlankFragment(private val info: TinderCard) : Fragment() {

    private lateinit var binding: FragmentBlankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentBlankBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlankBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.username.text = info.name
        binding.matchesCount.text = info.matches
        binding.userImage.setImageBitmap(info.image)
    }
}