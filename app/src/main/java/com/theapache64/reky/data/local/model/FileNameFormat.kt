package com.theapache64.reky.data.local.model

data class FileNameFormat(
    val regEx: Regex,
    val dateTimeFormat: String
) {
    data class Data(
        val name: String,
        val dateTime: Long
    )

    companion object {
        const val REGEX_KEY_NAME = "name"
        const val REGEX_KEY_DATE_TIME = "dateTime"
    }

    init {
        // Validation
        require(
            regEx.toString().contains(REGEX_KEY_NAME)
        ) { "Couldn't find '$REGEX_KEY_NAME' from given regex '$regEx'" }
        require(
            regEx.toString().contains(REGEX_KEY_DATE_TIME)
        ) { "Couldn't find '$REGEX_KEY_DATE_TIME' from given regex '$regEx'" }
    }

    fun parse(fileName: String): Data? {
        val matches = regEx.find(fileName)
        return if (matches != null) {
            val name = matches.groups[1]?.value.toString()
            val dateTime = matches.groups[2]?.value?.toLong()!!
            Data(name, dateTime)
        } else {
            null
        }
    }
}