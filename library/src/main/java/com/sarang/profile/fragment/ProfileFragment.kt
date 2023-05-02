package com.sarang.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sarang.profile.R
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.profile.databinding.FragmentProfileBinding
import com.sarang.profile.uistate.ProfileUiState
import com.sarang.profile.uistate.testProfileUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

/**
 * [ProfileRvAdt]
 * [ProfileInfoHolder]
 * [TimeLineVH]
 * [FragmentProfileBinding]
 */

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    //프로필 뷰모델
    private val mViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 바인딩 초기화
        val binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        // 바인딩에 라이프싸이클 오너 설정
        binding.lifecycleOwner = viewLifecycleOwner

        binding.ivSettings.setOnClickListener {
        }
        // UI 구독 실행
        subScribeUI(testProfileUiState(viewLifecycleOwner), binding)

        return binding.root
    }

    private fun subScribeUI(uiState: StateFlow<ProfileUiState>, binding: FragmentProfileBinding) {
        lifecycleScope.launch {
            uiState.collect {
                binding.profileUrl = it.profileUrl
                binding.feedCount = it.feedCount
                binding.follower = it.follower
                binding.following = it.following

                if(!it.isLogin){
                    findNavController().navigate(R.id.nonLoginProfileFragment)
                }
            }
        }
    }
}