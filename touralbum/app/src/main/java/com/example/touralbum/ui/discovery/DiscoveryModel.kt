package com.example.touralbum.ui.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiscoveryModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is discovery Fragment"
    }
    val text: LiveData<String> = _text
}