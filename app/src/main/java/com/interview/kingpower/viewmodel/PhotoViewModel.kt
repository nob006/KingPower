package com.interview.kingpower.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.kingpower.R
import com.interview.kingpower.model.PhotoRes
import com.interview.kingpower.repository.PhotoListRepository
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val photoListRepository: PhotoListRepository
) : ViewModel() {

    private var _photoRes = MutableLiveData<PhotoRes>()
    val photoRes: LiveData<PhotoRes> get() = _photoRes

    private var _showMessage = MutableLiveData<Int>()
    val showMessage: LiveData<Int> get() = _showMessage

    init {
        getPhotoList()
    }

    fun getPhotoList() {
        viewModelScope.launch {
            val res = photoListRepository.getPhotoList()
            if (res.isSuccessful) {
                _photoRes.value = res.body()
            } else {
                _showMessage.value = R.string.error_message
            }
        }
    }
}