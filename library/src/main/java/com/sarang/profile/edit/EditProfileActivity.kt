package com.sarang.profile.edit

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sarang.profile.R
import com.sarang.profile.databinding.ActivityEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private lateinit var progress: ProgressDialog

    private val editProfileViewModel: EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityEditProfileBinding>(
            this,
            R.layout.activity_edit_profile
        )
        progress = ProgressDialog(this)
        progress.setCancelable(false)

        binding.lifecycleOwner = this
        binding.viewModel = editProfileViewModel

        binding.btnCancel.setOnClickListener { finish() }
        binding.btnConfirm.setOnClickListener { editProfileViewModel.saveProfile() }

        /*val getContent = registerForActivityResult(InstagramGalleryContract()) {
            it?.getStringArrayListExtra("pictures")?.also {
                Logger.d(it.toString())
                editProfileViewModel.setNewProfileImage(it[0])
            }
        }*/

        /*binding.btnEditPicture.setOnClickListener {
            getContent.launch("a")
        }*/


        subScribeUI(binding)
    }

    private fun subScribeUI(binding: ActivityEditProfileBinding) {
//        editProfileViewModel.userData.observe(this) {
//            it?.let {
//                it.userName?.let {
//                    editProfileViewModel.setUserName(it)
//                }
//                it.profile_pic_url?.let {
//                    editProfileViewModel.setProfileImage(it)
//                }
//                it.email?.let {
//                    editProfileViewModel.setEmail(it)
//                }
//            }
//        }

        editProfileViewModel.completeEditProfile.observe(this) {
            if (it)
                finish()
        }

        editProfileViewModel.progress.observe(this) {
            if (it)
                progress.show()
            else
                progress.hide()
        }

        editProfileViewModel.error.observe(this) {
            showAlert(it)
        }
    }

    private fun showAlert(it: String?) {
        AlertDialog.Builder(this)
            .setTitle(it)
            .setPositiveButton("확인") { _, _ ->

            }
            .show()
    }
}