package com.capstone.core.data.common

import kotlinx.coroutines.Dispatchers

class MyDispatchers {
    val default = Dispatchers.Default
    val io = Dispatchers.IO
    val main = Dispatchers.Main
    val unconfined = Dispatchers.Unconfined
}