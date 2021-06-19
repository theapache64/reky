package com.theapache64.reky.data.local.model

import com.theapache64.reky.ui.composable.ListItem

data class Recording(
    val filePath: String
) : ListItem {
    override fun getCircleText(): String {
        TODO("Not yet implemented")
    }

    override fun getMainText(): String {
        TODO("Not yet implemented")
    }
}
