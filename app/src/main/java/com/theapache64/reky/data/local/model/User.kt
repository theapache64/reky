package com.theapache64.reky.data.local.model

import com.theapache64.reky.ui.composable.ListItem

/**
 * Created by theapache64 : May 30 Sun,2021 @ 01:10
 */
data class User(
    val contact: Contact,
    var recordCount: Int? = 1
) : ListItem {

    companion object {
        private val BEAUTIFY_REGEX = "([a-z])([A-Z])".toRegex()
        private fun beautify(name: String): String {
            return name.replace(regex = BEAUTIFY_REGEX, "$1 $2")
        }
    }

    override fun getCircleText(): String {
        return (recordCount ?: 0).toString()
    }

    override fun getMainText(): String {
        return beautify(contact.name)
    }

}