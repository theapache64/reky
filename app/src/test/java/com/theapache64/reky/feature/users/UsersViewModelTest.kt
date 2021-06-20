package com.theapache64.reky.feature.users

import com.github.theapache64.expekt.should
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.theapache64.reky.data.local.model.Config
import com.theapache64.reky.data.repo.ConfigRepo
import com.theapache64.reky.data.repo.ContactsRepo
import com.theapache64.reky.data.repo.RecordsRepo
import com.theapache64.reky.fakeContacts
import com.theapache64.reky.fakeRecords
import com.theapache64.reky.test.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

/**
 * Created by theapache64 : May 31 Mon,2021 @ 14:18
 */
class UsersViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()


    @Test
    fun `Returns correct data`() = runBlockingUnitTest {
        val viewModel = UsersViewModel(fakeRecordsRepo, fakeContactsRepo, fakeConfigRepo)
        val users = viewModel.users.first()
        val totalRecordsAvailable = fakeRecordsRepo.getRecords("").size
        val totalRecordCollected = users.sumBy { it.recordCount ?: 0 }
        totalRecordsAvailable.should.equal(totalRecordCollected)
    }
}