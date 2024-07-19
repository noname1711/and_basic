package com.example.myapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private lateinit var binding : ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //lắng nghe sự kiện click lên btnDate
        binding.btnDate.setOnClickListener {
            selectDate()
        }

    }

    private fun selectDate() {
        val cal = Calendar.getInstance()
        Log.wtf("tuhoc",cal.toString())
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        Log.wtf("tuhoc","$day/${month+1}/$year")

        val dp =DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            Toast.makeText(this, "$dayOfMonth/${month+1}/$year",Toast.LENGTH_SHORT).show()

            val dateBirth = "$dayOfMonth/${month+1}/$year"
            binding.txtDateSelected.text = dateBirth
            //simpledateformat để ép chuỗi thành định dạng ngày tháng
            val sdf = SimpleDateFormat("dd/mm/yyyy", Locale.US)   //ngôn ngữ định dạng là US
            val ngaysinh = sdf.parse(dateBirth)
            ngaysinh?.let {
                val ngaySinhTheoPhut = ngaysinh.time/60000    // time là tg theo mini giây lên phải chia cho 60000 để ra phút

                //thoi gian hien tai
                val currDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currDate?.let{
                    //chuyen sang phut
                    val currDateInMinute = currDate.time/60000

                    //tinh toan ngay hien tai - ngay sinh
                    val diff = currDateInMinute - ngaySinhTheoPhut
                    binding.txtAgeInMinute.text = diff.toString()  //gan kq vao o txtAgeInMinute
            }

            }


        },year,month,day)     //.show() ở đây thì ng dùng có thể nhập tg ở tương lai
        // để tránh thì gán vào 1 biến dp như sau và rồi show() dp ra
        dp.datePicker.maxDate = System.currentTimeMillis() - 86400000  //giới hạn thời gian tối đa chọn là hiện tại
        dp.show()
    }
}