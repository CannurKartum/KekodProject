package com.example.kekodproject

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(): ViewModel() {
    // List to store the keys of the current bottom nav items
    private val _bottomNavItems = MutableLiveData<MutableList<String>>().apply { value = mutableListOf("ego") }
    val bottomNavItems: LiveData<MutableList<String>> = _bottomNavItems

    // Track visibility of BottomNavigationView
    private val _isBottomNavVisible = MutableLiveData<Boolean>().apply { value = true }
    val isBottomNavVisible: LiveData<Boolean> = _isBottomNavVisible

    // Add a new item to the bottom nav
    fun addBottomNavItem(key: String): Boolean {
        _bottomNavItems.value?.let { items ->
            if (!items.contains(key)) {
                if (items.size < 5) {
                    items.add(key)
                    _bottomNavItems.value = items
                    return true
                }
            }
        }
        return false
    }

    // Remove an item from the bottom nav
    fun removeBottomNavItem(key: String) {
        _bottomNavItems.value?.let { items ->
            if (items.contains(key) && key != "ego") {
                items.remove(key)
                _bottomNavItems.value = items
            }
        }
    }

    // Set visibility of BottomNavigationView
    fun setBottomNavVisibility(isVisible: Boolean) {
        _isBottomNavVisible.value = isVisible
    }
}