package com.norio.danstest.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norio.danstest.network.ApiClient
import com.norio.danstest.network.ApiConstant
import com.norio.danstest.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private var mService =  ApiClient.getClient(ApiConstant.BASE_API).create(ApiService::class.java) as ApiService
    private var mutableListJob = MutableLiveData<List<ListJobResponseItem>>()
    private var mutableThrowable = MutableLiveData<Throwable>()
    val dataListJob: LiveData<List<ListJobResponseItem>> get() = mutableListJob
    val dataThrowable: LiveData< Throwable> get() = mutableThrowable

    fun getListJob(location:String, description: String, fullTime: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = mService.getListJob(location, description, fullTime)
                result.let {
                    if (it.isSuccessful) {
                        mutableListJob.value = it.body()
                    }
                }
            } catch (t: Throwable) {
                mutableThrowable.value = t
            }
        }
    }
}