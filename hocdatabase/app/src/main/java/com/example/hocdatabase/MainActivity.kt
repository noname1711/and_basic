package com.example.hocdatabase

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hocdatabase.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private var db:CopyDbHelper?= null  //cần khai báo db, riêng khai báo trên onCreate phải để là var chứ ko phải val
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sao chép db
        db = CopyDbHelper(this)
        db?.openDatabase()
    }
}