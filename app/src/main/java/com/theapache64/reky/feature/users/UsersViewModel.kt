package com.theapache64.reky.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.reky.data.local.model.Contact
import com.theapache64.reky.data.local.model.User
import com.theapache64.reky.data.repo.ConfigRepo
import com.theapache64.reky.data.repo.ContactsRepo
import com.theapache64.reky.data.repo.RecordsRepo
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
    private val _users = MutableStateFlow<List<User>>(listOf())
    val users = _users.asStateFlow()

    init {

        viewModelScope.launch {

            val recordsDir = configRepo.getConfig()!!.recordsDir
            val records = recordsRepo.getRecords(recordsDir)
            Timber.d("Records: ${records.size} ")
            val allContacts = contactsRepo.getContacts()
            val users = records
                // Sort by timestamp first
                .asSequence()
                .sortedBy {
                    val lastHyphenIndex = it.name.lastIndexOf('-')
                    val lastDotIndex = it.name.lastIndexOf('.')
                    it.name.substring(lastHyphenIndex, lastDotIndex).also {
                        Timber.d("Timestamp: $it")
                    }.toLong()
                }
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
                    if (identification.isNumber()) {
                        val idLength = identification.length
                        val last10Digits = identification.substring(idLength - 10, idLength)
                        // Search for the number in contacts
                        allContacts
                            .find { contact ->
                                val regex = "${last10Digits}\$".toRegex()
                                contact.number?.contains(regex) ?: false
                            }?.name // Get name
                            ?: identification // Couldn't find name let's move with the number then
                    } else {
                        // No need to check with contact. We already have a name
                        identification
                    }
                }
                // From name to contact
                .map { name ->
                    val contact = allContacts.find { contact -> contact.name == name } ?: Contact(
                        null,
                        name,
                        null
                    )

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

            _users.value = users
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
