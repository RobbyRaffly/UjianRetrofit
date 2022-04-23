package com.adl.ujianretrofit


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adl.ujianretrofit.adapter.TableRowAdapter
import com.adl.ujianretrofit.model.AbsenItemHistory
import com.adl.ujianretrofit.model.HistoryModel
import com.adl.ujianretrofit.model.ResponseGetHistory
import com.adl.ujianretrofit.services.RetrofitConfig
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class History : AppCompatActivity() {
    private lateinit var tableRecyclerView : RecyclerView
    private var hisList = ArrayList<HistoryModel>()
    private lateinit var tableRowAdapter: TableRowAdapter
    private lateinit var history : HistoryModel
    lateinit var lstHis : List<AbsenItemHistory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val bulanArr:Array<String> = arrayOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun","Jul", "Aug","Sept","Okt","Nov","Des")
        RetrofitConfig().getUser()
            .getHistory(
                userSekarang.toString(),"username"
            )
            .enqueue(object : Callback<ResponseGetHistory> {
                override fun onResponse(
                    call: Call<ResponseGetHistory>,
                    response: Response<ResponseGetHistory>
                ) {
                    hisList.clear()
                      Log.d("Response", response.body().toString())
                    val data: ResponseGetHistory? = response.body()

                    lstHis = data?.data?.absen as List<AbsenItemHistory>
                    lstHis.forEach {
                        val tanggal:String= it.tanggalMasuk.toString()
                        val tglKeluar:String = it.tanggalKeluar.toString()
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        val dt = LocalDateTime.parse(tanggal, formatter)
                        val dtCeckout = LocalDateTime.parse(tglKeluar, formatter)

                        val hari:Int=dt.dayOfMonth
                        val bulan:String=dt.month.toString()

                        val jamMasuk:String=dt.hour.toString()
                        val menitMasuk:String=dt.minute.toString()
                        val jamKeluar:String=dtCeckout.hour.toString()
                        val menitKeluar:String=dtCeckout.minute.toString()

                        hisList.add(HistoryModel("${hari} ${bulan}","${jamMasuk}:${menitMasuk}", "${jamKeluar}:${menitKeluar}" ))
                    }


                    GlobalScope.launch {
                        this@History.runOnUiThread({
                            tableRowAdapter= TableRowAdapter(hisList)
                            table_recycler_view.apply {
                                layoutManager = LinearLayoutManager(this@History)
                                adapter = tableRowAdapter
                            }
                            tableRowAdapter.notifyDataSetChanged()
                        })
                    }


                    Toast.makeText(this@History, "Ambil data history sukses", Toast.LENGTH_LONG)
                        .show()
                    //dataGenerate(data?.data?.adlNews as List<AdlNewsItem>)

                }

                override fun onFailure(call: Call<ResponseGetHistory>, t: Throwable) {
                    Toast.makeText(this@History, "Ambil data history gagal", Toast.LENGTH_LONG)
                        .show()
                    Log.d("Response", t.localizedMessage)
                }


            })



        tableRecyclerView = findViewById(R.id.table_recycler_view)
        tableRowAdapter = TableRowAdapter(hisList)

        tableRecyclerView.layoutManager = LinearLayoutManager(this)
        tableRecyclerView.adapter = tableRowAdapter

    }
}