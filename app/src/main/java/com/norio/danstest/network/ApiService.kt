package com.norio.danstest.network
import com.norio.danstest.home.ListJobResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(ApiConstant.LIST_JOB)
    suspend fun getListJob(
        @Query("location") location: String,
        @Query("description") description: String,
        @Query("full_time") fullTime: Boolean
    ): Response<List<ListJobResponseItem>>

    @GET(ApiConstant.DETAIL_JOB)
    suspend fun getDetailJob(
        @Path("job_id") jobId: String
    ): Response<ListJobResponseItem>

}