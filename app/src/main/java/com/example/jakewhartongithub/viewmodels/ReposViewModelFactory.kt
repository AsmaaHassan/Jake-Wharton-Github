package com.example.jakewhartongithub.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
/**
 * Created by Asmaa Hassan
 */

class ReposViewModelFactory(private val ctx: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReposViewModel(ctx) as T
    }
}