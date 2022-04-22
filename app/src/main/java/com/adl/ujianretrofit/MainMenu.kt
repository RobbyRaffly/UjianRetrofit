package com.adl.ujianretrofit


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainMenu : AppCompatActivity() {
    var ceckout:Boolean=false
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        btnCheckin.setOnClickListener(){
            if(ceckout==false) {
                val intent = Intent(this@MainMenu, CheckIn::class.java)
                //resultLauncher.launch(intent)

                startActivity(intent)
                ceckout=true
                btnCheckin.setText("CHECK OUT")
            }else{
                btnCheckin.setText("CHECK IN")
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
}