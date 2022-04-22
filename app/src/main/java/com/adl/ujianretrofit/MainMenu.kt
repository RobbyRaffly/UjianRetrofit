package com.adl.ujianretrofit


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.adl.ujianretrofit.model.ResponseAddData
import com.adl.ujianretrofit.model.ResponseCheckOut
import com.adl.ujianretrofit.services.RetrofitConfig
import kotlinx.android.synthetic.main.activity_check_in.*
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainMenu : AppCompatActivity() {
    var ceckout:Boolean=false
    lateinit var waktuCekOut:String
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            ceckout=true
            btnCheckin.setText("CHECK OUT")
            btnCheckin.setBackgroundColor(Color.parseColor("#E9D5DA"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        btnCheckin.setOnClickListener(){
            if(ceckout==false) {
                val intent = Intent(this@MainMenu, CheckIn::class.java)
                resultLauncher.launch(intent)

                //startActivity(intent)


            }else{
                var tanggal= Calendar.getInstance()
                val formatDate = "yyyy-MM-dd HH:mm:ss"
                val sdf = SimpleDateFormat(formatDate, Locale.US)
                waktuCekOut=sdf.format(tanggal.time)

                RetrofitConfig().getUser().updateDataAndImage(createRB(idAbsen.toString()),
                    uploadImage(photoURI,"foto"), createRB(userSekarang),
                    createRB(tanggalSekarang), createRB(waktuCekOut),
                    createRB(lokasi)).enqueue(
                    object: Callback<ResponseCheckOut> {
                        override fun onResponse(
                            call: Call<ResponseCheckOut>,
                            response: Response<ResponseCheckOut>
                        ) {
                            Log.e("Response",response.body().toString())
                            Toast.makeText(this@MainMenu,(response.body() as ResponseCheckOut).message,
                                Toast.LENGTH_LONG).show()
                                btnCheckin.setText("CHECK IN")
                                ceckout=false
                                btnCheckin.setBackgroundColor(Color.parseColor("#FF1818"))

                        }

                        override fun onFailure(call: Call<ResponseCheckOut>, t: Throwable) {
                            Log.e("error post data",t.localizedMessage.toString())
                            btnCheckin.setText("CHECK IN")
                            ceckout=true
                        }

                    }
                )

            }
        }

        btnHistory.setOnClickListener(){
            val intent = Intent(this@MainMenu,History::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener(){
            val intent = Intent(this@MainMenu,MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun createRB(data:String): RequestBody {
        return RequestBody.create(MultipartBody.FORM,data)
    }

    fun uploadImage(uri: Uri, param:String): MultipartBody.Part {
        val file: File = File(uri.path)
        val rb: RequestBody =  file.asRequestBody("image/jpeg".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(param,file.name,rb)

    }
}