package com.adl.ujianretrofit.services

import com.adl.ujianretrofit.model.ResponseAddData
import com.adl.ujianretrofit.model.ResponseLogin
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface InterUser {
    @Headers("x-api-key:A0867B853E8A6911C38827D19D3247FD")
    //@GET("api/user_table/all?X-Api-Key=A0867B853E8A6911C38827D19D3247FD&filter=&field=&start=&limit=&filters[0][co][0][fl]=username&filters[0][co][0][op]=equal&filters[0][co][0][vl]=&filters[0][co][0][lg]=:username&filters[0][co][1][fl]=password&filters[0][co][1][op]=equal&filters[0][co][1][vl]=:password&filters[0][co][1][lg]=and&sort_field=id_user&sort_order=ASC")
    //@GET("api/user_table/all?x-api-key:A0867B853E8A6911C38827D19D3247FD&filter=&field=&start=&limit=&filters[0][co][0][fl]=username&filters[0][co][1][fl]=password")
    @GET("api/user_table/all")
    fun getUserLogin(@Query("filter")filter:String): Call<ResponseLogin>


    @Multipart
    @Headers("X-Api-Key:6D83551EAC167A26DC10BB7609EA9AEF")
    @POST("api/absen/add")
    fun addDataAndImage(@Part file: MultipartBody.Part, @Part("username") username:RequestBody,
                        @Part("tanggal_masuk")tanggal_masuk:RequestBody,@Part("tanggal_keluar")tanggal_keluar:RequestBody,
                        @Part("lokasi_GPS")lokasi_GPS:RequestBody):Call<ResponseAddData>

}