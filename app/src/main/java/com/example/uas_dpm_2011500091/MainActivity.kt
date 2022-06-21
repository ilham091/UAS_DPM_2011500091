package com.example.uas_dpm_2011500091

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var adpdosen: Adapter_dosen
    private lateinit var dataspn: ArrayList<Lecturer>
    private lateinit var lvDatadosen: ListView
    private lateinit var linTidakAdaData: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvDatadosen = findViewById(R.id.lvDatadosen)
        linTidakAdaData = findViewById(R.id.linTidakAdaData)

        dataspn = ArrayList()
        adpdosen = Adapter_dosen(this@MainActivity, dataspn)

        lvDatadosen.adapter =adpdosen

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, Entri_Data::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh(){
        val db = Campuss(this@MainActivity)
        val data = db.tampil()
        repeat(dataspn.size) { dataspn.removeFirst()}
        if(data.count > 0 ){
            while(data.moveToNext()){
                val data = Lecturer(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpdosen.add(data)
                adpdosen.notifyDataSetChanged()
            }
            lvDatadosen.visibility = View.VISIBLE
            linTidakAdaData.visibility  = View.GONE
        } else {
            lvDatadosen.visibility = View.GONE
            linTidakAdaData.visibility = View.VISIBLE
        }
    }
}