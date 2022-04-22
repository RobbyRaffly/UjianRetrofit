package com.adl.ujianretrofit


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adl.ujianretrofit.adapter.TableRowAdapter
import com.adl.ujianretrofit.model.HistoryModel
import com.adl.ujianretrofit.model.ResponseGetHistory
import com.adl.ujianretrofit.services.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class History : AppCompatActivity() {
    private lateinit var tableRecyclerView : RecyclerView
    private var hisList = ArrayList<HistoryModel>()
    private lateinit var tableRowAdapter: TableRowAdapter
    private lateinit var history : HistoryModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        RetrofitConfig().getUser()
            .getHistory(
                userSekarang.toString(),"username"
            )
            .enqueue(object : Callback<ResponseGetHistory> {
                override fun onResponse(
                    call: Call<ResponseGetHistory>,
                    response: Response<ResponseGetHistory>
                ) {
                      Log.d("Response", response.body().toString())
                    val data: ResponseGetHistory? = response.body()
                    var tanggal:String= data?.data?.absen!![0]?.tanggalMasuk.toString()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                    val dt = LocalDate.parse(tanggal, formatter)

                    val hari:String=dt.dayOfWeek.toString()
                    var bulan:String=dt.month.toString()

                    hisList.add(HistoryModel(tanggal, hari, bulan))

                    Toast.makeText(this@History, "Login berhasil", Toast.LENGTH_LONG)
                        .show()
                    //dataGenerate(data?.data?.adlNews as List<AdlNewsItem>)

                }

                override fun onFailure(call: Call<ResponseGetHistory>, t: Throwable) {
                    Toast.makeText(this@History, "Login Gagal", Toast.LENGTH_LONG)
                        .show()
                }


            })



        tableRecyclerView = findViewById(R.id.table_recycler_view)
        tableRowAdapter = TableRowAdapter(hisList)

        tableRecyclerView.layoutManager = LinearLayoutManager(this)
        tableRecyclerView.adapter = tableRowAdapter

    }
}