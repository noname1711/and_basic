package com.example.codeapk

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

@Suppress("UNCHECKED_CAST")
class FileHelper {
    val FILENAME = "listinfo.dat"   //tránh trg hợp nhập sai trong quá trình đọc ghi nên nhập biến duy nhất là FILENAME
    //ghi tệp
    fun writeData(item: ArrayList<String>, context: Context){
        val fos: FileOutputStream = context.openFileOutput(FILENAME,Context.MODE_PRIVATE)
        val oas = ObjectOutputStream(fos)
        oas.writeObject(item)
        oas.close()
    }

    //đọc tệp
    fun readData(context: Context): ArrayList<String>{
        //lúc này chạy app thì sẽ bị lỗi vì ko tồn tại file listinfo.dat
        // để khắc phục ta dùng try-catch
        //nếu ko có itemlist thì tự gán item list bằng 1 danh sách rỗng và hiển thị lên list view ko có phần tử nào cả
        val itemList: ArrayList<String> = try {
            val fis: FileInputStream = context.openFileInput(FILENAME)
            val ois = ObjectInputStream(fis)
            ois.readObject() as ArrayList<String>    //ép sang kiểu list
        }catch (e: FileNotFoundException){
            ArrayList()
        }

        return itemList   //mục đích cuối là lấy được danh sách sau khi đọc tệp
    }
}