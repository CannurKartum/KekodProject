package com.example.kekodproject.ui


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import com.example.kekodproject.MainActivity
import com.example.kekodproject.R
import com.example.kekodproject.databinding.FragmentEgoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class EgoFragment : Fragment() {

    private var _binding: FragmentEgoBinding? = null
    private val binding get() = _binding!!
    private lateinit var switchMap: Map<String, Switch>
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEgoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchKindness = view.findViewById<Switch>(R.id.switch_kidness)
        val switchOptimism = view.findViewById<Switch>(R.id.switch_optimism)
        val switchHappiness = view.findViewById<Switch>(R.id.switch_happiness)
        val switchRespect = view.findViewById<Switch>(R.id.switch_respect)
        val switchEgo = view.findViewById<Switch>(R.id.switch_ego)
        val switchGiving = view.findViewById<Switch>(R.id.switch_giving)
        switchMap = mapOf(
            "kindness" to switchKindness,
            "optimism" to switchOptimism,
            "happiness" to switchHappiness,
            "respect" to switchRespect,
            "ego" to switchEgo,
            "giving" to switchGiving
        )
        bottomNavigationView = (activity as? MainActivity)?.findViewById(R.id.bottom_navigation)
        initializeBottomNavMenu()
        setUpSwitchListeners()
        switchEgo.isChecked = true

        if (switchEgo.isChecked) {
            disableOtherSwitches()
            bottomNavigationView?.visibility = View.GONE
        }

        switchEgo.setOnCheckedChangeListener { _, isChecked ->
            if (switchEgo.isChecked) {
                disableOtherSwitches()
                bottomNavigationView?.visibility = View.GONE
            } else {
                enableAllSwitches()
                bottomNavigationView?.visibility = View.VISIBLE
            }
        }

    }

    private fun initializeBottomNavMenu() {
        bottomNavigationView?.menu?.clear() // Clear any existing menu items
        bottomNavigationView?.menu?.add(Menu.NONE, "ego".hashCode(), Menu.NONE, "Ego")?.apply {
            setIcon(R.drawable.ic_ego) // Replace with your actual drawable for Ego
        }
    }

    private fun setUpSwitchListeners() {
        switchMap.forEach { (key, switch) ->
            switch.setOnCheckedChangeListener { _, isChecked ->
                when (key) {
                    "ego" -> {
                        if (isChecked) {
                            disableOtherSwitches()
                            bottomNavigationView?.visibility = View.GONE
                        } else {
                            enableAllSwitches()
                            bottomNavigationView?.visibility = View.VISIBLE
                        }
                    }
                    else -> {
                        if (isChecked) {
                            addMenuItemToBottomNav(key)
                        } else {
                            removeMenuItemFromBottomNav(key)
                        }
                    }
                }
            }
        }
    }

    private fun disableOtherSwitches() {
        switchMap.forEach { (key, switch) ->
            if (key != "ego") {
                switch.isEnabled = false
                switch.isChecked = false
                removeMenuItemFromBottomNav(key)  // Also remove items from bottom nav
            }
        }
    }

    private fun enableAllSwitches() {
        switchMap.forEach { (_, switch) ->
            switch.isEnabled = true
        }
    }

    private fun addMenuItemToBottomNav(key: String) {
        // Check if there are already 5 items in the BottomNavigationView
        bottomNavigationView?.let { bottomNav ->
            if (bottomNav.menu.size() >= 5) {
                // Show a toast message if the limit is reached
                Toast.makeText(requireContext(), "You cannot add more than 5 items to the bottom nav bar", Toast.LENGTH_SHORT).show()
                switchMap[key]?.isChecked = false  // Uncheck the switch
                return
            }
            // Add the new menu item if under the limit
            bottomNav.menu.add(Menu.NONE, key.hashCode(), Menu.NONE, key.capitalize()).apply {
                this?.setIcon(getIconForSwitch(key))
            }
        }
    }

    private fun removeMenuItemFromBottomNav(key: String) {
        bottomNavigationView?.menu?.removeItem(key.hashCode())
    }

    private fun getIconForSwitch(key: String): Int {
        return when (key) {
            "kindness" -> R.drawable.ic_kindness  // Replace with your actual drawable
            "optimism" -> R.drawable.ic_optimism  // Replace with your actual drawable
            "happiness" -> R.drawable.ic_happiness  // Replace with your actual drawable
            "respect" -> R.drawable.ic_respect  // Replace with your actual drawable
            "giving" -> R.drawable.ic_giving  // Replace with your actual drawable
            else -> R.drawable.ic_launcher_foreground  // Fallback icon
        }
    }
}