package com.theapache64.reky.data.repo

import java.io.File
import javax.inject.Inject

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:08
 */
class RecordsRepo @Inject constructor(

) {
    fun getRecords(): List<File> {
        val records = mutableListOf<File>()

        return records
    }
}