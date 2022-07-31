package com.sarang.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.torang_core.navigation.*
import com.example.torang_core.util.Logger
import com.sarang.profile.databinding.FragmentProfileBinding
import com.sarang.profile.edit.EditProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * [ProfileRvAdt]
 * [ProfileInfoHolder]
 * [TimeLineVH]
 * [FragmentProfileBinding]
 */

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var settingsNavigation: SettingsNavigation

    @Inject
    lateinit var loginNavigation: LoginNavigation

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
        binding.loginNavigation = loginNavigation

        binding.ivSettings.setOnClickListener {
            settingsNavigation.goSettings(requireActivity())
        }

        binding.icProfile.icProfile.btnEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.user = null

        // Sets BottomNavigation
        val navHostFragment =
            childFragmentManager.findFragmentById(binding.icProfile.fc.id) as NavHostFragment
        //val navController = navHostFragment.navController
        binding.icProfile.tlProfile.setupWithNavController(navHostFragment.navController)

        // UI 구독 실행
        subScribeUI(binding)

        return binding.root
    }

    private fun subScribeUI(binding: FragmentProfileBinding) {
        mViewModel.my.observe(viewLifecycleOwner) {
            Logger.d("사용자 변경")
            if (it != null) {
                binding.user = it.toUserData()
            } else {
                Logger.d("사용자 초기화")
                binding.user = null
            }
        }
    }
}