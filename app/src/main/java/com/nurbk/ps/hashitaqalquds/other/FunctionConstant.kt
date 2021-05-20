package com.nurbk.ps.hashitaqalquds.other

import android.app.*
import androidx.core.view.isVisible
import com.nurbk.ps.hashitaqalquds.databinding.ToolbarLayoutBinding
import kotlinx.android.synthetic.main.toolbar_layout.*


fun Activity.setToolbarView(
    view: ToolbarLayoutBinding,
    title: String,
    isMane: Boolean = false,
    idMenu: Int,
    onComplete: () -> Unit
): ToolbarLayoutBinding {

    view.tvTitle.text = title
    view.btnBack.isVisible = !isMane

    if (idMenu != 0) {
        view.btnMenu.isVisible = true
        view.menu.setImageResource(idMenu)
        view.btnMenu.setOnClickListener {
            onComplete()
        }
    }

    return view
}


//fun Activity.getSnackBar(@LayoutRes layoutId: Int, view: View, message: String): View {
////    val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
////
////    val custom = this.layoutInflater.inflate(layoutId, null)
////
////    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
////    val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
////    snackbarLayout.setPadding(0, 0, 0, 218)
////
////    custom.findViewById<RoundCornerProgressBar>(R.id.item_progress).progress = 100f
////
////    custom.findViewById<TextView>(R.id.txtMessage).text = message
////
////    snackbarLayout.addView(custom)
////    snackbar.show()
//    return custom
//}


