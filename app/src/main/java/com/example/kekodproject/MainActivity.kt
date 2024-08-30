package com.example.kekodproject

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kekodproject.databinding.ActivityMainBinding
import com.example.kekodproject.ui.EgoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        bottomNavigationView = binding.bottomNavigation
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        initializeBottomNavMenu()

        setupBottomNav()

        bottomNavigationView.setOnItemSelectedListener { item ->
            // Navigate to the appropriate fragment
            navController.navigate(item.itemId)
            true
        }
    }

    // Initialize the BottomNavigationView with the Ego item as the default
    private fun initializeBottomNavMenu() {
        bottomNavigationView.menu.clear() // Clear any existing menu items
        bottomNavigationView.menu.add(Menu.NONE, R.id.egoFragment, Menu.NONE, "Ego")?.apply {
            setIcon(R.drawable.ic_ego) // Replace with your actual drawable for Ego
        }
    }

    // Function to set up BottomNavigationView
    private fun setupBottomNav() {
        // Set up BottomNavigationView with NavController
        bottomNavigationView.setupWithNavController(navController)
    }

    // Function to add menu items dynamically to BottomNavigationView
    fun addMenuItemToBottomNav(key: String) {
        // Check if there are already 5 items in the BottomNavigationView
        if (bottomNavigationView.menu.size() >= 5) {
            // Show a toast message if the limit is reached
            Toast.makeText(this, "You cannot add more than 5 items to the bottom nav bar", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if the item is already in the menu to prevent duplicates
        if (bottomNavigationView.menu.findItem(getMenuItemIdForKey(key)) == null) {
            // Add the new menu item if under the limit
            bottomNavigationView.menu.add(Menu.NONE, getMenuItemIdForKey(key), Menu.NONE, key.capitalize()).apply {
                this?.setIcon(getIconForSwitch(key))
            }
        }
    }

    // Function to remove menu items dynamically from BottomNavigationView
    fun removeMenuItemFromBottomNav(key: String) {
        // Ensure we don't remove the default "Ego" menu item
        if (key != "ego") {
            bottomNavigationView.menu.removeItem(getMenuItemIdForKey(key))
        }
    }

    // Helper function to get menu item ID based on the key
    private fun getMenuItemIdForKey(key: String): Int {
        return when (key) {
            "kindness" -> R.id.kindnessFragment
            "optimism" -> R.id.optimismFragment
            "happiness" -> R.id.happinessFragment
            "respect" -> R.id.respectFragment
            "giving" -> R.id.givingFragment
            else -> throw IllegalArgumentException("Invalid key")
        }
    }

    // Helper function to get icons for each menu item
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