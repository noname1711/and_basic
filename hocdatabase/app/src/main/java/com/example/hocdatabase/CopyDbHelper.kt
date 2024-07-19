package com.example.hocdatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class CopyDbHelper(private val context: Context) {
    companion object{     //phương thức tĩnh
        private val DB_NAME = "TUHOCDB.db"   //khai báo biến để đỡ phải dùng TUHOCDB.db
    }
    fun openDatabase(): SQLiteDatabase{
        val dbFile = context.getDatabasePath(DB_NAME)
        val file = File(dbFile.toString())
        //kiểm tra xem file DB_NAME có tồn tại chưa
        if(file.exists()){      //nếu file có tồn tại
            Log.e("tuhoc","file đã tồn tại")
            //tồn tại thì chỉ in ra thông báo chứ không làm gì
        }
        else{
            copyDatabase(dbFile)    //nếu chưa thì copy database vào(trong trường hợp khách xóa db đi r)
        }
        return SQLiteDatabase.openDatabase(dbFile.path,null,SQLiteDatabase.OPEN_READWRITE)
        //hoàn thiện opendatabase
    }

    private fun copyDatabase(dbFile: File?) {   //hàm copy db
        val openDB = context.assets.open(DB_NAME)     //mở db
        val outputStream = FileOutputStream(dbFile)   //mở file và dùng outputstream để đẩy dữ liệu vào
        val buffer = ByteArray(1024)     //tách db ra thành từng đoạn và copy lần lượt từng file vào, ở đây chọn copy lần lượt 1024
        while (openDB.read(buffer)>0){  //đọc mà còn dữ liệu
            outputStream.write(buffer)   //thực hiện ghi
            Log.wtf("DB","Writting")    //in ra dòng thông báo
        }
        outputStream.flush()   //đẩy dữ liệu
        outputStream.close()   //đóng luồng
        openDB.close()        //đóng db
        Log.wtf("DB","cope database thành công")
    }
}