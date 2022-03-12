package ussr.retr.tinderbuyaboutscare.Fragments

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import ussr.retr.tinderbuyaboutscare.Objects.TinderCard
import ussr.retr.tinderbuyaboutscare.R
import ussr.retr.tinderbuyaboutscare.databinding.FragmentCardsBinding
import kotlin.math.abs


class CardsFragment : Fragment() {

    private lateinit var binding: FragmentCardsBinding
    var lastX: Float = 0f
    var lastY: Float = 0f

    var x: Float = 0f
    var y: Float = 0f

    var width: Int = 0
    var height: Int = 0

    var centerX: Float = 0f
    var centerY: Float = 0f
    var angle: Float = 0f

    private lateinit var information: MutableList<TinderCard>


    override fun getContext(): Context? {
        return super.getContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        information = mutableListOf(
            TinderCard(
                "Слава",
                "10",
                BitmapFactory.decodeResource(requireContext().resources, R.drawable.slava)
            ),
            TinderCard(
                "Слава",
                "1",
                BitmapFactory.decodeResource(requireContext().resources, R.drawable.slava)
            ),
            TinderCard(
                "Слава",
                "15",
                BitmapFactory.decodeResource(requireContext().resources, R.drawable.slava)
            ),
            TinderCard(
                "username",
                "100",
                BitmapFactory.decodeResource(requireContext().resources, R.drawable.jack)
            ),
            TinderCard(
                "User",
                "10",
                BitmapFactory.decodeResource(requireContext().resources, R.drawable.photo)
            ),
            TinderCard(
                "Слава",
                "1",
                BitmapFactory.decodeResource(requireContext().resources, R.drawable.slava)
            ),
            TinderCard(
                "Слава",
                "15",
                BitmapFactory.decodeResource(requireContext().resources, R.drawable.slava)
            ),
            TinderCard(
                "Слава",
                "100",
                BitmapFactory.decodeResource(requireContext().resources, R.drawable.slava)
            )
        )

        binding = FragmentCardsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun handleMove(view: View) {
        val difference = centerX - x
        angle = abs(difference)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        if (abs(difference) < width / 3) {
            if (difference > 0) { // move left
                angle *= -1
            }
            view.rotation = angle / 50
            view.alpha = 1.0f - angle / 1000
        } else {
            rebuildCards()
        }
    }

    private fun resetCard(view: View) {
        ObjectAnimator.ofFloat(view, "rotation", 0.0f).apply {
            duration = 100
            start()
        }
        ObjectAnimator.ofFloat(view, "alpha", 1.0f).apply {
            duration = 100
            start()
        }
    }

    private fun rebuildCards() {
        val swipedCard = binding.cardsContainer.children.first()
        binding.cardsContainer.removeView(swipedCard)
        if (insertFragment((swipedCard as ViewGroup).children.first().id)) {
            binding.cardsContainer.addView(swipedCard)
        }

        var count = binding.cardsContainer.childCount
        if (count > 1) {
            var iteration = 0
            binding.cardsContainer.children.forEach { view ->
                val lp = (view.layoutParams as ViewGroup.MarginLayoutParams)
                lp.bottomMargin = 30 * count
                view.setOnTouchListener(null)

                ObjectAnimator.ofFloat(view, "translationY", (-1 * 5 * count).toFloat()).apply {
                    duration = 100
                    start()
                }

                view.layoutParams = lp
                view.scaleX = 1.0f - 0.1f * iteration
                view.rotation = 0f
                view.alpha = 1.0f
                view.elevation = count.toFloat()

                iteration++
                count--
            }

            bindCard(binding.cardsContainer.children.first())
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialInitCards()
        rebuildCards()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun bindCard(view: View) {
        view.setOnTouchListener { _, e ->
            when (e.action) {
                2 -> {
                    x = e.x
                    y = e.y
                    handleMove(view)
                    lastX = x
                    lastY = y
                }
                1 -> {
                    resetCard(view)
                }
            }
            true
        }

        // init pivot
        view.viewTreeObserver.addOnGlobalLayoutListener {
            width = view.width
            height = view.height
            centerX = (width / 2).toFloat()
            centerY = (height / 2).toFloat()
            view.pivotX = (width / 2).toFloat()
            view.pivotY = height.toFloat()
        }
    }

    fun initialInitCards() {
        binding.cardsContainer.children.forEach { view ->
            insertFragment((view as ViewGroup).children.first().id)
        }
    }

    fun insertFragment(container: Int): Boolean {
        if (information.count() > 1) {
            val info = information.first()
            information.removeAt(0)

            childFragmentManager
                .beginTransaction()
                .replace(container, BlankFragment(info))
                .commit()
            return true
        } else {
            return false
        }
    }
}