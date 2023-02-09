package com.anikin.aleksandr.simplevocabulary.viewmodel

import io.reactivex.Observable

interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}