package com.theapache64.reky.feature.users

import androidx.lifecycle.ViewModel
import com.theapache64.reky.data.repo.ContactsRepo
import com.theapache64.reky.data.repo.RecordsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:08
 */
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val recordsRepo: RecordsRepo,
    private val contactsRepo: ContactsRepo,
) : ViewModel() {

}