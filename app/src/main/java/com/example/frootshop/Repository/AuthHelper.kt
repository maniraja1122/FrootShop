package com.example.frootshop.Repository

import android.util.Log
import android.widget.Toast
import com.example.frootshop.Classes.Fruits
import com.example.frootshop.Classes.Shop
import com.example.frootshop.Classes.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.regex.Pattern

object AuthHelper {
    var db:FirebaseDatabase= FirebaseDatabase.getInstance()
    var auth:FirebaseAuth=FirebaseAuth.getInstance()
    var CurrentUser:User;
    var ShopsList:MutableList<Shop>;
    var SelectedShop=0
    var SelectedFruit=Fruits()


    //Email Checker
    fun isValidEmailId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
    fun CreateUser(){
        var user=User(auth.currentUser!!.uid)
        db.getReference().child("Users").child(user.id!!).setValue(user)
    }
    fun UpdateUser(){
        db.getReference().child("Users").child(CurrentUser.id!!).setValue(CurrentUser)
    }
    fun SetUser(){
        db.getReference().child("Users").child(auth.uid.toString()).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               CurrentUser=snapshot.getValue(User::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun AddShop(shop:Shop){
        var key=db.getReference().child("Shops").push().key!!
        db.getReference().child("Shops").child(key).setValue(shop)
    }
    fun AddFruit(){
        CurrentUser.cartlist!!.add(SelectedFruit)
        UpdateUser()
    }
    fun EmptyCart(){
        CurrentUser.cartlist!!.clear()
        UpdateUser()
    }
    fun SetShopList(){
        db.getReference().child("Shops").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                ShopsList=snapshot.children.mapNotNull {it.getValue(Shop::class.java) }.toMutableList()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    init {
      db.setPersistenceEnabled(true)
        CurrentUser=User()
        ShopsList= mutableListOf()
    }
}