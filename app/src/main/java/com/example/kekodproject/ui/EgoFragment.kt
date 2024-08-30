package com.example.kekodproject.ui


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.example.kekodproject.MainActivity
import com.example.kekodproject.R
import com.example.kekodproject.databinding.FragmentEgoBinding


class EgoFragment : Fragment() {

    private var _binding: FragmentEgoBinding? = null
    private val binding get() = _binding!!
    private lateinit var switchMap: Map<String, Switch>

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

        switchEgo.isChecked = true

        if (switchEgo.isChecked) {
            disableOtherSwitches()
        }

        switchEgo.setOnCheckedChangeListener { _, isChecked ->
            if (switchEgo.isChecked) {
                disableOtherSwitches()
            } else {
                enableAllSwitches()
            }
        }

    }

    private fun disableOtherSwitches() {
        switchMap.forEach { (key, switch) ->
            if (key != "ego") {
                switch.isEnabled = false
                switch.isChecked = false
            }
        }
    }

    private fun enableAllSwitches() {
        switchMap.forEach { (_, switch) ->
            switch.isEnabled = true
        }
    }
}