package com.adl.ujianretrofit


import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.adl.ujianretrofit.model.ResponseAddData
import com.adl.ujianretrofit.services.RetrofitConfig
import com.github.drjacky.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_check_in.*
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

class CheckIn : AppCompatActivity(), LocationListener {
    var addImage:Boolean=false
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: EditText
    private val locationPermissionCode = 2


    lateinit var photoURI: Uri
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!

                Photo.setImageURI(uri)
                photoURI=uri
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)
        setupUI()
        //getLocation()
    }


    private fun setupUI() {

        btnSend.setOnClickListener(){
            if(addImage==false){
                cameraLauncher.launch(
                    ImagePicker.with(this)
                        .crop()
                        .cameraOnly()
                        .maxResultSize(480, 800, true)
                        .createIntent()
                )

                var tanggal=Calendar.getInstance()
                val formatDate = "yyyy-MM-dd HH:mm:ss"
                val sdf = SimpleDateFormat(formatDate, Locale.US)
                var tanggalSekarang=sdf.format(tanggal.time)

                RetrofitConfig().getUser().addDataAndImage(uploadImage(photoURI,"lampiran"), createRB("admin"), createRB(tanggalSekarang.toString()), createRB(""),
                createRB("2 , 3")).enqueue(
                    object:Callback<ResponseAddData>{
                        override fun onResponse(
                            call: Call<ResponseAddData>,
                            response: Response<ResponseAddData>
                        ) {
                            Toast.makeText(this@CheckIn,(response.body() as ResponseAddData).message,Toast.LENGTH_LONG).show()
                            btnSend.setText("DONE")
                            addImage==true
                        }

                        override fun onFailure(call: Call<ResponseAddData>, t: Throwable) {
                            Log.e("error post data",t.localizedMessage.toString())
                            btnSend.setText("RE-SCAN")
                        }

                    }
                )
            }else{
                addImage=false
                val intent = Intent(this@CheckIn, MainMenu::class.java)
                startActivity(intent)
            }

        }
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {
        tvGpsLocation = findViewById(R.id.txtGeo)
        tvGpsLocation.setText(location.latitude.toString() +","+location.longitude.toString())
        // tvGpsLocation.text = "Latitude: " + location.latitude.toString() + " , Longitude: " + location.longitude
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
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