package com.example.grocery_shop.view.activity

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseActivity
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.databinding.ActivityEditProfileBinding
import com.example.grocery_shop.model.user.UserEditBody
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.view.fragment.CustomerFragment
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import android.R.attr.data
import android.net.Uri
import android.R.attr.data
import android.util.Log
import com.example.grocery_shop.view.fragment.DetailProductFragment


private const val PERMISSION_CODE = 1001
private const val IMAGE_PICK_CODE = 1000

class EditProfileActivity : BaseVMActivity<ActivityEditProfileBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()


    override fun initView() {
        getInfoUser()
        binding.etRealName.isEnabled = false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initListener() {

        binding.layoutSubmit.setOnClickListener {
//            editProfile(null, binding.etRealName.text.toString(), binding.etUserName.text.toString(),binding.etEmail.text.toString(),  binding.etPhone.text.toString() )
        }

        binding.ivEditImage.setOnClickListener {
            checkPerPermission()
        }
        binding.toolbar.setOnClickListener {
            (applicationContext as? HomeActivity?)?.replaceFragmentFullScreen(
                DetailProductFragment(),
                true
            )
        }
    }

    override fun initData() {

    }

    private fun getInfoUser() {
        binding.etRealName.setText(UserManager.getUserName(applicationContext))
        binding.etUserName.setText(UserManager.getFullName(applicationContext))
        binding.etEmail.setText(UserManager.getEmail(applicationContext))
        binding.etPhone.setText(UserManager.getPhone(applicationContext))
    }

    private fun chooseImageLocal() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPerPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
            requestPermissions(permissions, PERMISSION_CODE);
        } else {
            chooseImageLocal()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    chooseImageLocal()
                } else {
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            data?.let {
                viewModel.compressorImage(UserManager.getUserId(applicationContext).toString(), it, onComplete = { bit ->
                    binding.ivUserImage.setImageBitmap(bit)
                })
            }
        }
    }

    private fun editProfile(
        avatar: String? = null,
        username: String,
        fullName: String,
        email: String,
        phone: String
    ) {
        var body = UserEditBody(avatar, username, fullName, email, phone)
        viewModels.editProfile(
            UserManager.getUserId(applicationContext).toString(),
            body,
            onComplete = { data ->
                Toast.makeText(applicationContext, "Oke", Toast.LENGTH_SHORT).show()
            }, onErrors = {
                Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show()

            })
    }
}