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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.kekodproject.MainActivity
import com.example.kekodproject.MainActivityViewModel
import com.example.kekodproject.R
import com.example.kekodproject.databinding.FragmentEgoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class EgoFragment : Fragment() {
    private var _binding: FragmentEgoBinding? = null
    private val binding get() = _binding!!

    private lateinit var switchMap: Map<String, Switch>
    private lateinit var mainActivity: MainActivity
    private lateinit var bottomNavViewModel: MainActivityViewModel

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

        // Get reference to MainActivity
        mainActivity = activity as MainActivity
        // Initialize ViewModel
        bottomNavViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

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

        // Restore switch states when the fragment is created
        restoreSwitchStates()
        setUpSwitchListeners()

        // Set initial visibility of BottomNavigationView based on "ego" switch state
        if (switchEgo.isChecked) {
            bottomNavViewModel.setBottomNavVisibility(false)  // Hide BottomNavigationView if "ego" is checked
        } else {
            bottomNavViewModel.setBottomNavVisibility(true)   // Show BottomNavigationView if "ego" is not checked
        }

        // Handle the initial visibility of the BottomNavigationView
        bottomNavViewModel.isBottomNavVisible.observe(viewLifecycleOwner) { isVisible ->
            mainActivity.bottomNavigationView.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    private fun restoreSwitchStates() {
        // Restore the state of each switch based on ViewModel data
        switchMap.forEach { (key, switch) ->
            switch.isChecked = bottomNavViewModel.getSwitchState(key)
            // Disable switches if "ego" is enabled
            if (key != "ego" && bottomNavViewModel.getSwitchState("ego")) {
                switch.isEnabled = false
            } else {
                switch.isEnabled = true
            }
        }
    }

    private fun setUpSwitchListeners() {
        switchMap.forEach { (key, switch) ->
            switch.setOnCheckedChangeListener { _, isChecked ->
                when (key) {
                    "ego" -> handleEgoSwitch(isChecked)
                    else -> handleOtherSwitches(key, isChecked)
                }
                // Save state to ViewModel
                bottomNavViewModel.updateSwitchState(key, isChecked)
            }
        }
    }

    private fun handleEgoSwitch(isChecked: Boolean) {
        if (isChecked) {
            disableOtherSwitches()
            bottomNavViewModel.setBottomNavVisibility(false)
        } else {
            enableAllSwitches()
            bottomNavViewModel.setBottomNavVisibility(true)
        }
    }

    private fun handleOtherSwitches(key: String, isChecked: Boolean) {
        if (isChecked) {
            if (bottomNavViewModel.bottomNavItems.value?.size ?: 0 >= 5) {
                Toast.makeText(requireContext(), "You cannot add more than 5 items to the bottom nav bar", Toast.LENGTH_SHORT).show()
                switchMap[key]?.isChecked = false
            } else {
                bottomNavViewModel.addBottomNavItem(key)  // Add item to ViewModel
            }
        } else {
            bottomNavViewModel.removeBottomNavItem(key)  // Remove item from ViewModel
        }
    }

    private fun disableOtherSwitches() {
        switchMap.forEach { (key, switch) ->
            if (key != "ego") {
                switch.isEnabled = false
                switch.isChecked = false
                bottomNavViewModel.removeBottomNavItem(key)  // Remove from ViewModel as well
            }
        }
    }

    private fun enableAllSwitches() {
        switchMap.forEach { (key, switch) ->
            switch.isEnabled = true
        }
    }
}