package com.theapache64.reky.ui.screen.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.reky.data.local.model.Contact
import com.theapache64.reky.data.local.model.User
import com.theapache64.reky.data.repo.ConfigRepo
import com.theapache64.reky.data.repo.ContactsRepo
import com.theapache64.reky.data.repo.RecordsRepo
import com.theapache64.reky.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:08
 */
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val recordsRepo: RecordsRepo,
    private val contactsRepo: ContactsRepo,
    private val configRepo: ConfigRepo
) : ViewModel() {

    private val _users = MutableStateFlow<Resource<List<User>>>(Resource.Idle())
    val users = _users.asStateFlow()


    private var fullUsers: List<User>? = null
    private val _searchKeyword = MutableStateFlow("")
    val searchKeyword = _searchKeyword.asStateFlow()

    init {

        viewModelScope.launch {

            _users.value = Resource.Loading()

            val recordsDir = configRepo.getConfig()!!.recordsDir
            val records = recordsRepo.getRecords(
                recordsDir = recordsDir,
                fileNameFormat = configRepo.getFileNameFormat()!!
            )
            Timber.d("Records: ${records.size} ")
            val allContacts = contactsRepo.getContacts()
            val users = records
                // then get name/number
                .map {
                    val lastHyphenIndex = it.name.lastIndexOf('-')
                    val identification = it.name.substring(0, lastHyphenIndex)
                    identification
                }
                // Remove duplicates
                .toSet()
                // Find name for saved numbers
                .asSequence()
                .map { identification ->
                    val contact = if (identification.isNumber()) {
                        val idLength = identification.length
                        val last10Digits = identification.substring(idLength - 10, idLength)
                        // Search for the number in contacts
                        allContacts
                            .find { contact ->
                                val regex = "${last10Digits}\$".toRegex()
                                contact.number?.contains(regex) ?: false
                            } ?: Contact( // or dummy contact
                            null,
                            identification,
                            null
                        )
                    } else {
                        // No need to check with contact. We already have a name
                        allContacts.find { contact -> contact.name == identification } ?: Contact(
                            null,
                            identification,
                            null
                        )
                    }

                    // From name to contact
                    User(contact)
                }
                // Remove duplicate
                .toSet()
                // Set records count
                .map { user ->
                    val contact = user.contact
                    val recordCount = records.count { record ->
                        val isNameMatched = record.name.contains(contact.name, ignoreCase = true)
                        if (isNameMatched) {
                            true
                        } else {
                            val numLength = contact.number?.length ?: 0
                            if (numLength >= 10) {
                                val last10Digits =
                                    contact.number?.substring(numLength - 10, numLength)
                                record.name.contains("${last10Digits}-")
                            } else {
                                false
                            }
                        }
                    }
                    user.apply {
                        this.recordCount = recordCount
                    }
                }
                .toList()

            fullUsers = users.toList()
            _users.value = Resource.Success(users)
        }
    }


    fun onSearchKeywordChanged(newSearchKeyword: String) {
        _searchKeyword.value = newSearchKeyword

        val resp = _users.value
        fullUsers?.let { fullUsers ->
            if (resp is Resource.Success) {
                val filtered = if (newSearchKeyword.isNotBlank()) {
                    // show filtered
                    fullUsers.filter {
                        it.getMainText().contains(newSearchKeyword, ignoreCase = true)
                    }
                } else {
                    // show full
                    fullUsers
                }

                _users.value = Resource.Success(filtered)
            }
        }

    }
}

private fun String.isNumber(): Boolean {
    return try {
        toLong()
        true
    } catch (e: NumberFormatException) {
        false
    }
}
