package com.example.grocery_shop.customview.diaglog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.grocery_shop.R
import com.example.grocery_shop.databinding.DialogConfirmBinding

class DialogConfirm(val context: Context) {

    private val binding by lazy {
        DialogConfirmBinding.inflate(LayoutInflater.from(context))
    }
    private val dialog: Dialog by lazy {
        AlertDialog.Builder(context).setView(binding.root).create()
    }
    init {
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
    }

    fun showDialogConfirm(
        content: String) {
        binding.dialogTwoactionDescss.text = content
        binding.dialogYes.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun isShowing(): Boolean {
        return dialog.isShowing
    }

    fun hide() {
        dialog.dismiss()
    }
}