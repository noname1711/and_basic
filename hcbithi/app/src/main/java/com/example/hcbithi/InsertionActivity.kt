package com.example.hcbithi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    //khai báo biến
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        //xử lí sưj kiện trên nút save
        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            saveEmployeeData()   //alt enter
        }

    }

    private fun saveEmployeeData() {
        //lấy giá trị
        val edtEmpName = findViewById<EditText>(R.id.edtEmpName)
        val empName = edtEmpName.text.toString()
        val edtEmpAge = findViewById<EditText>(R.id.edtEmpAge)
        val empAge = edtEmpAge.text.toString()
        val edtEmpSalary = findViewById<EditText>(R.id.edtEmpSalary)
        val empSalary = edtEmpSalary.text.toString()

        //đẩy dữ liệu người dùng nhập lên database
        val empId = dbRef.push().key!! //khóa chính
        val employee = EmployeeModel(empId,empName,empAge,empSalary)   //truyền toàn bộ dữ liệu ng dùng nhập lên db

        //kiểm tra các ô nhập liệu đã có dữ liệu hay chưa, nếu chưa thì sẽ hiện biểu tượng lỗi đỏ ở ô đấy
        if (empName.isEmpty()){
            edtEmpName.error = "vui lòng nhập tên"
            return
        }
        if (empAge.isEmpty()){
            edtEmpAge.error = "vui lòng nhập tuổi"
            return
        }
        if (empSalary.isEmpty()){
            edtEmpSalary.error = "vui lòng nhập lương"
            return
        }

        dbRef.child(empId).setValue(employee)   //chèn db, child để thêm cột con trong employee
            .addOnCompleteListener {
                Toast.makeText(this,"thêm data thành công",Toast.LENGTH_SHORT).show()
                //thêm xong thì reset ô nhập liệu về rỗng
                edtEmpName.setText("")
                edtEmpAge.setText("")
                edtEmpSalary.setText("")
            }   //khi thành công thì thông báo cho ng dùng biết
            .addOnFailureListener {err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_SHORT).show()
                //${err.message}  để xuất ra thông báo lỗi
            }  //nếu thất bại thì thông báo cho ng dùng biết
    }
}