package com.theapache64.reky.test

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.theapache64.reky.data.local.model.Config
import com.theapache64.reky.data.repo.ConfigRepo
import com.theapache64.reky.data.repo.ContactsRepo
import com.theapache64.reky.data.repo.RecordsRepo
import com.theapache64.reky.fakeContacts
import com.theapache64.reky.fakeRecords
import kotlinx.coroutines.runBlocking

val fakeContactsRepo = mock<ContactsRepo>().apply {
    runBlocking {
        whenever(getContacts()).thenReturn(fakeContacts)
    }
}

val fakeRecordsRepo = mock<RecordsRepo>().apply {
    runBlocking {
        whenever(getRecords(any())).thenReturn(fakeRecords)
        whenever(getDurationInMillis(any())).thenReturn(10000)
    }
}

val fakeConfigRepo = mock<ConfigRepo>().apply {
    runBlocking {
        whenever(getConfig()).thenReturn(Config("/some/dir/"))
    }
}