package com.ismartcoding.plain.ui.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class About2Dialog : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MyList()
            }
        }
    }

    @Composable
    fun MyList() {
        Column {
            CustomListItem("test")
        }
    }

    @Composable
    fun CustomListItem(text: String) {
//        ListItem(
//            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
//            text = Text(text),
//        )
    }
}