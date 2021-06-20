package com.theapache64.reky

import com.theapache64.reky.data.local.model.Contact
import java.io.File

/**
 * Created by theapache64 : May 31 Mon,2021 @ 15:03
 */
val fakeContactNameVenom = "Venom"
val fakeContactNumberVenom = "0987654321"

val fakeContacts = listOf(
    Contact("1", "JohnWick", "1234567890"),
    Contact("2", fakeContactNameVenom, fakeContactNumberVenom),
    Contact("3", "Hulk", "7896541230"),
    Contact("4", "Thanos", "3216549870"),
)

val fakeRecords = listOf(
    File("JohnWick-20210422094853.aac"),
    File("JohnWick-20210422046853.aac"),
    File("JohnWick-20210422046321.aac"),
    File("$fakeContactNameVenom-20210422094810.aac"),
    File("$fakeContactNameVenom-20210422094444.aac"),
    File("3216549870-20210422091444.aac"), // Thanos
    File("4569871230-20210422094414.aac"), // Unknown
    File("7412589630-20210422094414.aac"), // Unknown
    File("$fakeContactNumberVenom-2021042209654.aac"), // Venom
)
