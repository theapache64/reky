package com.theapache64.reky.data.local.model

import com.theapache64.reky.ui.composable.ListItem
import java.io.File

data class Recording(
    val duration: String,
    val recordedAt: String,
    val file: File
) : ListItem {

    override fun getCircleText(): String {
        return duration
    }

    override fun getMainText(): String {
        return recordedAt
    }
}
