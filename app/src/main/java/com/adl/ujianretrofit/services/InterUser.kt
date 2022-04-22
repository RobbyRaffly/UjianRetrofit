package com.adl.ujianretrofit.services

import com.adl.ujianretrofit.model.ResponseLogin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface InterUser {
    @Headers("x-api-key:A0867B853E8A6911C38827D19D3247FD")
    //@GET("api/user_table/all?X-Api-Key=A0867B853E8A6911C38827D19D3247FD&filter=&field=&start=&limit=&filters[0][co][0][fl]=username&filters[0][co][0][op]=equal&filters[0][co][0][vl]=&filters[0][co][0][lg]=:usernameand&filters[0][co][1][fl]=password&filters[0][co][1][op]=equal&filters[0][co][1][vl]=:password&filters[0][co][1][lg]=and&sort_field=id_user&sort_order=ASC")
    @GET("api/user_table/all?filter=&field=&start=&limit=&filters[0][co][0][fl]=username&filters[0][co][1][fl]=password")
    //@GET("api/user_table/all?")
    fun getUserLogin(@Query("username") username:String, @Query("password")password:String): Call<ResponseLogin>
}