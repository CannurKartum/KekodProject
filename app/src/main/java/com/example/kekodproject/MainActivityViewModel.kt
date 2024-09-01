package com.example.kekodproject

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainActivityViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    // Keys for SavedStateHandle
    companion object {
        private const val SWITCH_STATE_KEY = "switch_state_key"
    }

    // List to store the keys of the current bottom nav items
    private val _bottomNavItems = MutableLiveData<MutableList<String>>().apply { value = mutableListOf("ego") }
    val bottomNavItems: LiveData<MutableList<String>> = _bottomNavItems

    // Track visibility of BottomNavigationView
    private val _isBottomNavVisible = MutableLiveData<Boolean>().apply { value = true }
    val isBottomNavVisible: LiveData<Boolean> = _isBottomNavVisible

    // Initialize switch states with SavedStateHandle
    private val _switchStates = savedStateHandle.getLiveData<Map<String, Boolean>>(SWITCH_STATE_KEY, defaultSwitchStates())
    val switchStates: LiveData<Map<String, Boolean>> = _switchStates

    private fun defaultSwitchStates(): Map<String, Boolean> {
        return mapOf(
            "kindness" to false,
            "optimism" to false,
            "happiness" to false,
            "respect" to false,
            "ego" to true,  // ego is enabled by default
            "giving" to false
        )
    }

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

    // Update switch state and save to SavedStateHandle
    fun updateSwitchState(key: String, isChecked: Boolean) {
        val currentStates = _switchStates.value?.toMutableMap() ?: mutableMapOf()
        currentStates[key] = isChecked
        _switchStates.value = currentStates
        savedStateHandle[SWITCH_STATE_KEY] = currentStates
    }

    // Retrieve the current state of a switch
    fun getSwitchState(key: String): Boolean {
        return _switchStates.value?.get(key) ?: false
    }
}