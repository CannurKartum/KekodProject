package com.example.kekodproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kekodproject.databinding.FragmentGivingBinding


class GivingFragment : Fragment() {
    private var _binding: FragmentGivingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        _binding = FragmentGivingBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}