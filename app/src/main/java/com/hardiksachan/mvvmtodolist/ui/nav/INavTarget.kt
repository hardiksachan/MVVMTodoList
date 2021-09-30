package com.hardiksachan.mvvmtodolist.ui.nav

import androidx.navigation.NamedNavArgument

interface INavTarget {
    val arguments: List<NamedNavArgument>

    val destination: String
}