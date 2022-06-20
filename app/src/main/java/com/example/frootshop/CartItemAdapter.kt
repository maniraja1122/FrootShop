package com.example.frootshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frootshop.Classes.Fruits
import com.example.frootshop.Repository.AuthHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class CartItemAdapter(var fruitlist:MutableList<Fruits>):RecyclerView.Adapter<CartItemAdapter.MyViewHolder>() {
    class MyViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var cartimage=view.findViewById<ImageView>(R.id.cartimage)
        var cartname=view.findViewById<TextView>(R.id.cartname)
        var carttotal=view.findViewById<TextView>(R.id.carttotal)
        var cartprice=view.findViewById<TextView>(R.id.cartprice)
        var cartdec=view.findViewById<TextView>(R.id.cartdec)
        var cartquantity=view.findViewById<TextView>(R.id.cartquantity)
        var cartinc=view.findViewById<TextView>(R.id.cartinc)
        var delbtn=view.findViewById<ImageButton>(R.id.delbtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cartitem,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        CoroutineScope(Dispatchers.Main).launch {
//            async {
                Glide.with(holder.itemView.context).load(fruitlist[position].imgurl)
                    .into(holder.cartimage);
//            }
//        }
        holder.cartname.setText(fruitlist[position].name)
        holder.cartprice.setText("$"+fruitlist[position].price.toString())
        holder.cartquantity.setText((fruitlist[position].quantity.toString()))
        holder.carttotal.setText("$"+(fruitlist[position].quantity*fruitlist[position].price).toInt().toString())
        holder.cartinc.setOnClickListener(){
            if(fruitlist[position].quantity<10){
                fruitlist[position].quantity+=1
                AuthHelper.UpdateUser()
                holder.cartquantity.setText((fruitlist[position].quantity.toString()))
                holder.carttotal.setText("$"+(fruitlist[position].quantity*fruitlist[position].price).toInt().toString())
            }
        }
        holder.cartdec.setOnClickListener(){
            if(fruitlist[position].quantity>1){
                fruitlist[position].quantity-=1
                AuthHelper.UpdateUser()
                holder.cartquantity.setText((fruitlist[position].quantity.toString()))
                holder.carttotal.setText("$"+(fruitlist[position].quantity*fruitlist[position].price).toInt().toString())
            }
        }
        holder.delbtn.setOnClickListener(){
            fruitlist.removeAt(position)
            AuthHelper.UpdateUser()
        }
    }

    override fun getItemCount(): Int {
        return fruitlist.size
    }
}