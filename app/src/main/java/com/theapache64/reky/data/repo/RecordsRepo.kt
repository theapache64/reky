package com.theapache64.reky.data.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:08
 */
class RecordsRepo @Inject constructor(

) {
    suspend fun getRecords(recordsDir: String): List<File> = withContext(Dispatchers.IO) {
        File(recordsDir).listFiles()?.toList()!!
    }
}