package com.example.hcbithi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hcbithi.adapter.EmpAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {

    //khai báo biến
    private lateinit var ds:ArrayList<EmployeeModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        val rvEmp = findViewById<RecyclerView>(R.id.rvEmp)
        rvEmp.layoutManager = LinearLayoutManager(this)  //có thể điều hướng theo ngang or dọc, ko truyền mặc định là dọc
        rvEmp.setHasFixedSize(true)
        ds = arrayListOf<EmployeeModel>()
        GetThongTinNV()    //alt enter

    }

    private fun GetThongTinNV() {
        //khi đang lấy data thì recycler view bị mất còn chữ loading data sẽ hiện lên
        val rvEmp = findViewById<RecyclerView>(R.id.rvEmp)
        rvEmp.visibility = View.GONE
        val txtLoadingData = findViewById< TextView>(R.id.txtLoadingData)
        txtLoadingData.visibility= View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        //để đọc dữ liệu tại một đường dẫn và lắng nghe các thay đổi
        dbRef.addValueEventListener(object: ValueEventListener{
            //ctrl + i
            override fun onDataChange(snapshot: DataSnapshot) {
                //có sự thay đổi thì chụp ảnh data, nếu có thì true, ko thì false
                ds.clear()  //xóa danh sách
                //để phòng trường hợp khi người dùng click qua lại hoặc pause activity làm sao đó mà có gọi lại hàm thì ds chỉ chứa thông tin từ firebase thôi, chứ không nếu nó có sẵn thì sẽ bị chồng thêm dữ liệu lấy từ firebase về .
                if (snapshot.exists()){    //nếu tồn tại snapshot, tức là có dữ liệu
                    for(empSnap in snapshot.children){   //chạy từng dòng 1 ở ảnh chụp dữ liệu
                        val empData = empSnap.getValue(EmployeeModel::class.java)  //gán data vào 1 biến
                        ds.add(empData!!) //add phần tử đó vào danh sách này
                    }
                    val mAdapter = EmpAdapter(ds)   //gọi ra danh sách
                    rvEmp.adapter = mAdapter

                    //lắng nghe sự kiện click lên item rv
                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener{
                        //ctrl+i
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity,EmployeeDetailsActivity::class.java)
                            //chuyển từ màn hình hiện tại là FetchingActivity sang màn hình EmployeeDatailsActivity
                            //đặt dữ liệu vào và gửi đi từ màn hình FetchingActivity
                            intent.putExtra("empId",ds[position].empId)
                            //lấy vị trí trên danh sách để nhận empId
                            intent.putExtra("empName",ds[position].empName)
                            intent.putExtra("empAge",ds[position].empAge)
                            intent.putExtra("empSalary",ds[position].empSalary)
                            startActivity(intent)
                        }
                    })

                    // load xong data thì ẩn chữ loading và hiện recycler view
                    rvEmp.visibility = View.VISIBLE
                    txtLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}