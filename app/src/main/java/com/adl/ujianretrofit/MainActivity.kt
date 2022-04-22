package com.adl.ujianretrofit

import android.content.Intent
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

lateinit var userSekarang:String
var idUser:Int=0
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin.setOnClickListener() {

            RetrofitConfig().getUser()
                .getUserLogin(txtUsername.text.toString(),"username"
                )
                .enqueue(object : Callback<ResponseLogin> {
                    override fun onResponse(
                        call: Call<ResponseLogin>,
                        response: Response<ResponseLogin>
                    ) {
                      //  Log.d("Response", response.body().toString())
                        val data: ResponseLogin? = response.body()
                        userSekarang= data?.data?.userTable!![0]?.username.toString()
                        idUser= data?.data?.userTable!![0]?.idUser?.toInt() ?:
                        Log.d("Response", userSekarang.toString() )
                        Toast.makeText(this@MainActivity, "Login berhasil", Toast.LENGTH_LONG)
                            .show()
                        //dataGenerate(data?.data?.adlNews as List<AdlNewsItem>)
                        val intent = Intent(this@MainActivity, MainMenu::class.java)
                        startActivity(intent)

                    }

                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Login Gagal", Toast.LENGTH_LONG)
                            .show()
                    }


                })
        }
    }
}