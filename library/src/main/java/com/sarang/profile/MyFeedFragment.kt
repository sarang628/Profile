package com.sarang.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sarang.profile.databinding.FragmentMyFeedBinding
import com.sryang.torang_core.navigation.MenuBottomSheetNavigation
import com.sryang.torang_core.navigation.MyMenuBottomSheetNavigation
import com.sryang.torang_core.navigation.NotLoggedInMenuBottomSheetNavigation
import com.sryang.torang_core.navigation.ReportNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFeedFragment : Fragment() {

    private val mViewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var menuBottomSheetNavigation: MenuBottomSheetNavigation

    @Inject
    lateinit var myMenuBottomSheetNavigation: MyMenuBottomSheetNavigation

    @Inject
    lateinit var notLoggedInMenuBottomSheetNavigation: NotLoggedInMenuBottomSheetNavigation

    @Inject
    lateinit var reportNavigation: ReportNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMyFeedBinding.inflate(layoutInflater, container, false)

        binding.rvMyFeed.adapter =
            MyFeedRvAdt(mViewModel, childFragmentManager, viewLifecycleOwner)

        /*viewLifecycleOwner.lifecycle.addObserver(
            ReportProcessor(
                mViewModel,
                menuBottomSheetNavigation,
                myMenuBottomSheetNavigation,
                notLoggedInMenuBottomSheetNavigation,
                reportNavigation,
                requireContext()
            )
        )*/

        return binding.root
    }
}