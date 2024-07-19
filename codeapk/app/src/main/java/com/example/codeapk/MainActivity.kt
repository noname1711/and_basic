package com.example.codeapk

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.codeapk.databinding.ActivityMainBinding

private lateinit var binding : ActivityMainBinding
class MainActivity : AppCompatActivity() {

    //khai báo các biến
    var itemList = ArrayList<String>()
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //list view
        itemList = fileHelper.readData(this)
        val arrayAdapter= ArrayAdapter(this,
            android.R.layout.simple_list_item_1,android.R.id.text1,
            itemList)
        binding.lvCongViec.adapter = arrayAdapter
        //nút add
        binding.btnAdd.setOnClickListener {
            val itemName = binding.edtInput.text.toString()
            itemList.add(itemName)
            //ng dùng click nút add thì thêm item vào list
            //đưa ô nhập liệu sau khi thêm về rỗng
            binding.edtInput.setText("")
            //ghi data để itemlist lưu trên máy khách hàng chứ ko phải thanh ram
            fileHelper.writeData(itemList,applicationContext)
            arrayAdapter.notifyDataSetChanged()   //thông báo data đã đc thay đổi để listview biết mà update
        }
        
        //gọi alert dialog để xóa item hoặc ko
        binding.lvCongViec.setOnItemClickListener { parent, view, position, id ->
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Có chắc muốn xóa")
            alert.setCancelable(true)
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                itemList.removeAt(position)  //xóa ở đúng vị trí position
                arrayAdapter.notifyDataSetChanged()
                //ghi danh sách mới vào tệp trên máy khách hàng
                fileHelper.writeData(itemList,applicationContext)
            } )
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            alert.create()
            alert.show()
        }

    }
}