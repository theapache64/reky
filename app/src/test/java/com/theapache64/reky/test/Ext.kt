package com.theapache64.reky.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

/**
 * Created by theapache64 : May 31 Mon,2021 @ 15:16
 */
fun runBlockingUnitTest(block: suspend CoroutineScope.() -> Unit) = runBlocking {
    block()
}