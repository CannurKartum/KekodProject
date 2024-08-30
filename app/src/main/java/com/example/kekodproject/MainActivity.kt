package com.example.kekodproject

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var bottomNavViewModel: MainActivityViewModel


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
        bottomNavViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        setupBottomNav()

        // Observe ViewModel for changes in BottomNavigationView items and visibility
        bottomNavViewModel.bottomNavItems.observe(this) { items ->
            updateBottomNavMenu(items)
        }

        bottomNavViewModel.isBottomNavVisible.observe(this) { isVisible ->
            bottomNavigationView.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    private fun setupBottomNav() {
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId)
            true
        }
    }

    private fun updateBottomNavMenu(items: List<String>) {
        bottomNavigationView.menu.clear()
        items.forEach { key ->
            bottomNavigationView.menu.add(Menu.NONE, getMenuItemIdForKey(key), Menu.NONE, key.capitalize()).apply {
                setIcon(getIconForSwitch(key))
            }
        }
    }

    fun addItemToBottomNav(key: String) {
        if (!bottomNavViewModel.addBottomNavItem(key)) {
            Toast.makeText(this, "You cannot add more than 5 items to the bottom nav bar", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to handle getting menu item ID based on the key
    private fun getMenuItemIdForKey(key: String): Int {
        return when (key) {
            "kindness" -> R.id.kindnessFragment
            "optimism" -> R.id.optimismFragment
            "happiness" -> R.id.happinessFragment
            "respect" -> R.id.respectFragment
            "giving" -> R.id.givingFragment
            "ego" -> R.id.egoFragment
            else -> throw IllegalArgumentException("Invalid key")
        }
    }

    // Function to handle getting icons for menu items
    private fun getIconForSwitch(key: String): Int {
        return when (key) {
            "kindness" -> R.drawable.ic_kindness
            "optimism" -> R.drawable.ic_optimism
            "happiness" -> R.drawable.ic_happiness
            "respect" -> R.drawable.ic_respect
            "giving" -> R.drawable.ic_giving
            "ego" -> R.drawable.ic_ego
            else -> R.drawable.ic_launcher_foreground
        }
    }
}