package com.example.hcbithi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hcbithi.databinding.ActivityMainBinding

private lateinit var binding : ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //nút btnInsertData để chuyển màn hình
        binding.btnInsertData.setOnClickListener{
            val intent = Intent(this,InsertionActivity::class.java)
            startActivity(intent)
        }

        //nút btnFetchData để chuyển màn hình
        binding.btnFetchData.setOnClickListener{
            val intent = Intent(this,FetchingActivity::class.java)
            startActivity(intent)
        }
    }
}