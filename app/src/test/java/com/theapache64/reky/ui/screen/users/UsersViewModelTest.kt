package com.theapache64.reky.ui.screen.users

import com.github.theapache64.expekt.should
import com.theapache64.reky.data.local.model.User
import com.theapache64.reky.test.*
import com.theapache64.reky.util.Resource
import kotlinx.coroutines.flow.first
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
        val users = (viewModel.users.first() as Resource.Success<List<User>>).data
        val totalRecordsAvailable = fakeRecordsRepo.getRecords("").size
        val totalRecordCollected = users.sumBy { it.recordCount ?: 0 }
        totalRecordsAvailable.should.equal(totalRecordCollected)
    }
}