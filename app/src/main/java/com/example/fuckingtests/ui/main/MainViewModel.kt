package com.example.fuckingtests.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuckingtests.models.User
import com.example.fuckingtests.network.JSONPlaceHolderApi
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel: ViewModel() {

    init {
        Log.d(TAG, "MainViewModel - init")
    }

    override fun onCleared() {
        super.onCleared()

        Log.d(TAG, "MainViewModel - onCleared")
    }

    val users: MutableLiveData<List<User>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun loadUsers() {
        viewModelScope.launch {
            try {
                users.value = JSONPlaceHolderApi.retrofitService.loadUsers()
            } catch (e: Exception) {
                e.printStackTrace()
                error.value = e.message
            }
        }
    }

    companion object {
        const val TAG = "MainViewModelTAG"
    }
}