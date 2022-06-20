package com.example.frootshop

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frootshop.Classes.Fruits
import com.example.frootshop.Repository.AuthHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FruitListAdapter(var fruitlist:MutableList<Fruits>,var context: Context):RecyclerView.Adapter<FruitListAdapter.MyViewHolder> (){
    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        var fruitimg=itemview.findViewById<ImageView>(R.id.fruitimg)
        var fruitname=itemview.findViewById<TextView>(R.id.fruitname)
        var fruitprice = itemview.findViewById<TextView>(R.id.fruitprice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fruitshopitem,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.fruitname.setText(fruitlist[position].name)
        holder.fruitprice.setText("$"+fruitlist[position].price.toString())
        CoroutineScope(Dispatchers.Main).launch {
            async {
                Glide.with(holder.itemView.context).load(fruitlist[position].imgurl)
                    .into(holder.fruitimg);
            }}
        holder.itemView.setOnClickListener(){
            Log.v("mani","clicked "+fruitlist[position].name)
            AuthHelper.SelectedFruit=fruitlist[position]
            context.startActivity(Intent(context,FruitSelected::class.java))
        }
    }

    override fun getItemCount(): Int {
        return fruitlist.size
    }
}