package com.theapache64.reky.ui.screen.user

import androidx.lifecycle.SavedStateHandle
import com.github.theapache64.expekt.should
import com.theapache64.reky.fakeContactNameVenom
import com.theapache64.reky.fakeContactNumberVenom
import com.theapache64.reky.test.MainCoroutineRule
import com.theapache64.reky.test.fakeConfigRepo
import com.theapache64.reky.test.fakeRecordsRepo
import com.theapache64.reky.test.runBlockingUnitTest
import com.theapache64.reky.util.Resource
import kotlinx.coroutines.flow.first
import org.junit.Rule
import org.junit.Test

class UserViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()


    @Test
    fun `Returns correct data`() = runBlockingUnitTest {
        val viewModel = UserViewModel(
            recordsRepo = fakeRecordsRepo,
            configRepo = fakeConfigRepo,
            savedStateHandle = SavedStateHandle(
                mapOf(
                    UserViewModel.KEY_USER_NAME to fakeContactNameVenom,
                    UserViewModel.KEY_USER_MOBILE to fakeContactNumberVenom
                )
            )
        )

        val recordings = viewModel.recordings.first() as Resource.Success
        recordings.data.size.should.equal(3)

        // Analyse 1st
        recordings.data[0].let { firstRecording ->
            firstRecording.duration
        }
    }
}