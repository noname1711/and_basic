package com.example.hcbithi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        setValueToView()
        //nút delete
        val btnDelete= findViewById<Button>(R.id.btnDelete)
        btnDelete.setOnClickListener{
            deleteRecord(intent.getStringExtra("empId").toString())
            //alt + enter
        }

        //nút update
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        btnUpdate.setOnClickListener{
            //alt +enter
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )  //đưa thông tin cần sửa lên hoọp thoại dialog
        }
    }

    private fun openUpdateDialog(empId: String, empName: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater //biến đổi layout xml thành view
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)  //truyền layout là uodate dialog và để mặc định
        mDialog.setView(mDialogView)

        //update thông tin vào dialog
        val etEmpName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etEmpAge = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etEmpSalary = mDialogView.findViewById<EditText>(R.id.etEmpSalary)
        val btnUpdate = mDialogView.findViewById<Button>(R.id.btnUpdate)

        //hứng thông tin từ fetching activity và truyền lên hôpj thoại dialog
        etEmpName.setText(intent.getStringExtra("empName").toString())
        etEmpAge.setText(intent.getStringExtra("empAge").toString())
        etEmpSalary.setText(intent.getStringExtra("empSalary").toString())

        mDialog.setTitle("Update $empName Record")
        val alertDialog = mDialog.create()
        alertDialog.show()   //hiển thị dialog lên

        //click vào btnUpdate
        btnUpdate.setOnClickListener{
            updateEmpData(
                empId,
                etEmpName.text.toString(),
                etEmpAge.text.toString(),
                etEmpSalary.text.toString()
            )   //update 4 thông số: id từ hệ thống giữ nguyên và 3 cái còn lại lấy trên update dialog để sửa
            //update xong đưa ra tb
            Toast.makeText(applicationContext,"Data đã update",Toast.LENGTH_SHORT).show()
            //update lại data trên activity_employee_details
            val tvEmpName = findViewById<TextView>(R.id.tvEmpName)
            val tvEmpAge = findViewById<TextView>(R.id.tvEmpAge)
            val tvEmpSalary = findViewById<TextView>(R.id.tvEmpSalary)
            tvEmpName.setText(etEmpName.text.toString())
            tvEmpAge.setText(etEmpAge.text.toString())
            tvEmpSalary.setText(etEmpSalary.text.toString())

            alertDialog.dismiss()  //update xong thì đóng hộp thoại lại
        }
    }

    private fun updateEmpData(id: String, name: String, age: String, salary: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeeModel(id,name,age,salary)
        dbRef.setValue(empInfo)
        //tương tác vs db, tg tác vs dòng có id mà fetchingactivity truyền sang,
        //truyền 4 tham số là data mà ng dùng truyèn trên 3 ô chỉnh sửa và update
    }

    private fun  deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        // xóa dòng con theo id

        dbRef.child(id).removeValue().addOnSuccessListener{
            Toast.makeText(this,"employee data đã xóa",Toast.LENGTH_SHORT).show()
            finish()
              //nếu xóa thành công tắt màn hình hiện tại và mở lại mh FetchingActivity
        }.addOnFailureListener {err ->
            Toast.makeText(this,"xóa lỗi ${err.message}",Toast.LENGTH_SHORT).show()
        }  //nếu xóa thất bại
    }

    private fun setValueToView() {
        val tvEmpId = findViewById<TextView>(R.id.tvEmpId)
        val tvEmpName = findViewById<TextView>(R.id.tvEmpName)
        val tvEmpAge = findViewById<TextView>(R.id.tvEmpAge)
        val tvEmpSalary = findViewById<TextView>(R.id.tvEmpSalary)
        //hứng dữ liệu đc gửi từ FetchingActivity và gán vào các ô data
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpAge.text = intent.getStringExtra("empAge")
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpSalary.text = intent.getStringExtra("empSalary")
    }
}