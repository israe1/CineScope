package com.israelmekomou.domain.fake

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import kotlinx.coroutines.flow.Flow

suspend fun <T : Any> Flow<PagingData<T>>.collectPagingItems(): List<T> =
    asSnapshot()
