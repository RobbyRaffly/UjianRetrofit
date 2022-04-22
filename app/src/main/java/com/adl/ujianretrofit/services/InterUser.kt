package com.adl.ujianretrofit.services

import com.adl.ujianretrofit.model.ResponseAddData
import com.adl.ujianretrofit.model.ResponseCheckOut
import com.adl.ujianretrofit.model.ResponseGetAbsen
import com.adl.ujianretrofit.model.ResponseLogin
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface InterUser {
    @Headers("x-api-key:A0867B853E8A6911C38827D19D3247FD")
    @GET("api/user_table/all")
    fun getUserLogin(@Query("filter")filters:String,@Query("field")field:String): Call<ResponseLogin>

    @Headers("x-api-key:A0867B853E8A6911C38827D19D3247FD")
    @GET("api/absen/all")
    fun getDataAbsen(@Query("limit")limit:String,@Query("sort_order")sort_order:String): Call<ResponseGetAbsen>


    @Multipart
    @Headers("X-Api-Key:A0867B853E8A6911C38827D19D3247FD")
    @POST("api/absen/add")
    fun addDataAndImage(@Part file: MultipartBody.Part, @Part("username") username:RequestBody,
                        @Part("tanggal_masuk")tanggal_masuk:RequestBody, @Part("tanggal_keluar")tanggal_keluar:RequestBody,
                        @Part("lokasi_GPS")lokasi_GPS:RequestBody):Call<ResponseAddData>

    @Multipart
    @Headers("X-Api-Key:A0867B853E8A6911C38827D19D3247FD")
    @POST("api/absen/update")
    fun updateDataAndImage(@Part("id") id:RequestBody, @Part file: MultipartBody.Part, @Part("username") username:RequestBody,
                        @Part("tanggal_masuk")tanggal_masuk:RequestBody,@Part("tanggal_keluar")tanggal_keluar:RequestBody,
                        @Part("lokasi_GPS")lokasi_GPS:RequestBody):Call<ResponseCheckOut>


}