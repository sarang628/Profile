package com.sarang.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.torang_core.navigation.*
import com.example.torang_core.util.Logger
import com.sarang.base_feed.ReportProcessor
import com.sarang.profile.databinding.FragmentProfile1Binding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Profile1Fragment : Fragment() {

    @Inject
    lateinit var loginNavigation: LoginNavigation

    @Inject
    lateinit var menuBottomSheetNavigation: MenuBottomSheetNavigation

    @Inject
    lateinit var myMenuBottomSheetNavigation: MyMenuBottomSheetNavigation

    @Inject
    lateinit var notLoggedInMenuBottomSheetNavigation: NotLoggedInMenuBottomSheetNavigation

    @Inject
    lateinit var reportNavigation: ReportNavigation

    //프로필 뷰모델
    private val mViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // 바인딩 초기화
        val binding = FragmentProfile1Binding.inflate(layoutInflater, container, false)

        // 바인딩에 라이프싸이클 오너 설정
        binding.lifecycleOwner = viewLifecycleOwner

        binding.user = null

        // UI 구독 실행
        subScribeUI(binding)

        return binding.root
    }

    private fun subScribeUI(binding: FragmentProfile1Binding) {
        var userId = requireActivity().intent.getIntExtra("userId", 0)
        mViewModel.getUser(userId).observe(viewLifecycleOwner) {
            binding.user = it
        }

        // 리싸이클러뷰에 아답터 적용
        binding.icProfile.rvReview.adapter =
            ProfileRvAdt(mViewModel, mViewModel.getFeed(userId), viewLifecycleOwner)

        viewLifecycleOwner.lifecycle.addObserver(
            ReportProcessor(
                mViewModel,
                menuBottomSheetNavigation,
                myMenuBottomSheetNavigation,
                notLoggedInMenuBottomSheetNavigation,
                reportNavigation,
                requireContext()
            )
        )

        mViewModel.nothingProfile.observe(viewLifecycleOwner) {
            if(it == null)
                nothingProfile()
        }
    }

    private fun nothingProfile() {
        AlertDialog.Builder(requireContext())
            .setMessage("프로필 정보가 없습니다.")
            .setCancelable(true)
            .setPositiveButton("확인") { _, _ ->
                requireActivity().finish()
            }
            .show()
    }
}