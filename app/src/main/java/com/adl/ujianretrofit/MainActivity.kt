package com.adl.ujianretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.adl.ujianretrofit.model.ResponseLogin
import com.adl.ujianretrofit.services.RetrofitConfig
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin.setOnClickListener() {

            RetrofitConfig().getUser()
                .getUserLogin(txtUsername.text.toString(), txtPassword.text.toString())
                .enqueue(object : Callback<ResponseLogin> {
                    override fun onResponse(
                        call: Call<ResponseLogin>,
                        response: Response<ResponseLogin>
                    ) {
                        Log.d("Response", response.body().toString())
                        val data: ResponseLogin? = response.body()


                        //dataGenerate(data?.data?.adlNews as List<AdlNewsItem>)


                    }

                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG)
                            .show()
                    }


                })
        }
    }
}