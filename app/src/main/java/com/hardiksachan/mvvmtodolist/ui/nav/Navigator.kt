package com.hardiksachan.mvvmtodolist.ui.nav

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {

    private val _navEventStream =
        MutableSharedFlow<INavTarget>(extraBufferCapacity = 1)
    val pageStream = _navEventStream.asSharedFlow()

    fun navigateTo(navTarget: INavTarget) {
        _navEventStream.tryEmit(navTarget)
    }

}