package com.nurbk.ps.hashitaqalquds.other

import android.app.*
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.databinding.ToolbarLayoutBinding
import kotlinx.android.synthetic.main.toolbar_layout.*


fun Activity.setToolbarView(
    view: ToolbarLayoutBinding,
    title: String,
    isMane: Boolean = false,
    idMenu: Int,
    onComplete: (Int) -> Unit
): ToolbarLayoutBinding {


    view.tvTitle.text = title
    view.btnBack.isVisible = !isMane
    view.btnBack.setOnClickListener {
        onComplete(TWO)
    }
    if (idMenu != 0) {
        view.btnMenu.isVisible = true
        view.menu.setImageResource(idMenu)
        view.btnMenu.setOnClickListener {
            onComplete(ONE)
        }
    }

    return view
}


