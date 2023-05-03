package com.fitpeo.fitpeoassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitpeo.fitpeoassignment.model.Album
import com.fitpeo.fitpeoassignment.repository.MainRepository
import com.fitpeo.fitpeoassignment.service.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val liveDataList : MutableLiveData<Resource<List<Album>>> = MutableLiveData<Resource<List<Album>>>()

    fun getDetails()  = viewModelScope.launch {

        repository.getApiDetails().let {
            if (it.isSuccessful){
                liveDataList.postValue(Resource.success(it.body()))
            }else{
                liveDataList.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }

    }
}