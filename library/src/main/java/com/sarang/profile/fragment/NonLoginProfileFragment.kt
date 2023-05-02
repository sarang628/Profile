package com.sarang.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sarang.profile.R
import com.sarang.profile.databinding.FragmentNonLoginProfileBinding
import com.sarang.profile.uistate.ProfileUiState
import com.sarang.profile.uistate.testProfileUiState1
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NonLoginProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNonLoginProfileBinding.inflate(layoutInflater, container, false)

        binding.btnLogin.setOnClickListener {
            //startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        subScribeUI(testProfileUiState1(viewLifecycleOwner), binding)

        return binding.root
    }

    private fun subScribeUI(uiState: StateFlow<ProfileUiState>, binding: FragmentNonLoginProfileBinding) {
        lifecycleScope.launch {
            uiState.collect {
                if(it.isLogin){
                    findNavController().navigate(R.id.profileFragment2)
                }
            }
        }
    }
}