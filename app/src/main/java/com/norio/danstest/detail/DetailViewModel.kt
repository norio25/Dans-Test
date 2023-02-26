package com.norio.danstest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norio.danstest.home.ListJobResponseItem
import com.norio.danstest.network.ApiClient
import com.norio.danstest.network.ApiConstant
import com.norio.danstest.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private var mService =
        ApiClient.getClient(ApiConstant.BASE_API).create(ApiService::class.java) as ApiService

    private var mutableDetail = MutableLiveData<ListJobResponseItem>()
    private var mutableThrowable = MutableLiveData<Throwable>()
    val dataDetail: LiveData<ListJobResponseItem> get() = mutableDetail
    val dataThrowable: LiveData<Throwable> get() = mutableThrowable

    fun getDetail(jobId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = mService.getDetailJob(jobId)
                result.let {
                    if (it.isSuccessful) {
                        mutableDetail.value = it.body()
                    }
                }
            } catch (t: Throwable) {
                mutableThrowable.value = t
            }
        }
    }
}