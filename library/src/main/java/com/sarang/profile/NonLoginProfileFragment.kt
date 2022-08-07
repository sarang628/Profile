package com.sarang.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sarang.profile.databinding.FragmentNonLoginProfileBinding

class NonLoginProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNonLoginProfileBinding.inflate(layoutInflater, container, false)

        binding.btnLogin.setOnClickListener {
            //startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        return binding.root
    }
}