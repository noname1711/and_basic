package com.example.hokmi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadcastAirPlane: BroadcastReceiver() {
    //ctrl + i
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirPlaneMode: Boolean = intent!!.getBooleanExtra("state",false)
        if (isAirPlaneMode){
            Toast.makeText(context,"chế độ máy bay đang bật",
                Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(context,"chế độ máy bay đang tắt",
                Toast.LENGTH_SHORT).show()
        }

    }
}