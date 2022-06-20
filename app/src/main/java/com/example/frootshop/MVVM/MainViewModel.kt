package com.example.frootshop.MVVM

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frootshop.Classes.Fruits
import com.example.frootshop.Classes.Shop
import com.example.frootshop.Classes.User
import com.example.frootshop.Repository.AuthHelper

class MainViewModel:ViewModel() {
    var FruitList:MutableLiveData<MutableList<Fruits>> = MutableLiveData()
    fun UpdateList(){
            FruitList.value=AuthHelper.CurrentUser.cartlist
    }
    init{
        FruitList.value=AuthHelper.CurrentUser.cartlist
    }
}