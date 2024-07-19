package com.example.hcbithi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hcbithi.EmployeeModel
import com.example.hcbithi.R

class EmpAdapter(private val ds:ArrayList<EmployeeModel>) : RecyclerView.Adapter<EmpAdapter.ViewHolder>() {

    //code adapter lắng nghe sự kiện,đây là một mẹo code, thực tế khi intent cx ko cần làm thế này
    //dùng interface ở đây  là một mẹo code ngắn nhất (ai cũng dùng) để gọi một hàm xử lý không thuộc adapter bên trong adapter
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    //tạo class viewholder
    class ViewHolder(itemView: View,clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        init{
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    //ctrl + i
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.emp_list_item,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val tvEmpName = findViewById<TextView>(R.id.tvEmpName)
            tvEmpName.text = ds[position].empName
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}