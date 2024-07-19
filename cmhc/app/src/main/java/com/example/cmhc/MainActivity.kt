package com.example.cmhc

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cmhc.databinding.ActivityMainBinding

private lateinit var binding : ActivityMainBinding
class MainActivity : AppCompatActivity() {

    //khai báo các biến
    var dem =0   //tăng dem trên button
    var send:String? = null
    var message:String? = null
    var isChecked:Boolean? = null
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDem.setOnClickListener {
            dem ++   //click lên btn thì tăng biến đếm lên r gắn làm string trên nút
            binding.btnDem.text = dem.toString()
        }

    }

    // lưu trạng thái (phải ở hàm Pause)
    override fun onPause() {
        super.onPause()
        saveData()
    }

    //khôi phục dữ liệu(phải ở hàm Resume)
    override fun onResume(){
        super.onResume()
        goiData()
    }


    private fun saveData() {
        sharedPreferences = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)
        //lưu data ở dạng file là saveData, mode_private để chỉ truy cập đc trong ứng dụng chứ ngoài ứng dụng ko truy cập đc
        send = binding.edtSend.text.toString()  //lấy thông tin trên edtSend gán vào biến send
        message = binding.edtMessage.text.toString()    //lấy thông tin trên edtMessage gán vào biến message
        isChecked = binding.chkRemember.isChecked  //lấy trạng thái trên check box xem có đang check hay ko

        //lưu thông tin (key phải là duy nhất, tức là khi đặt tên key phải đặt tên khác nhau)
        val editor = sharedPreferences.edit()
        editor.putString("key_send",send)
        editor.putString("key_mes",message)
        editor.putInt("key_dem",dem)
        editor.putBoolean("key_remember",isChecked!!)
        editor.apply()
        Toast.makeText(applicationContext,"Data đã được lưu",Toast.LENGTH_SHORT).show()
    }


    private fun goiData() {
        sharedPreferences = this.getSharedPreferences("saveData",Context.MODE_PRIVATE)
        send = sharedPreferences.getString("key_send",null)
        // lấy theo key đã lưu, gtri nếu ko tìm thấy là null
        message = sharedPreferences.getString("key_mes",null)
        dem = sharedPreferences.getInt("key_dem",0)  //ko tìm thấy thì hiện 0
        isChecked = sharedPreferences.getBoolean("key_remember",false)
        // hiện dữ liệu đã lưu
        binding.edtSend.setText(send)
        binding.edtMessage.setText(message)
        binding.btnDem.text = dem.toString()
        binding.chkRemember.isChecked = isChecked!!

    }
}