package com.example.bremonthrestaurant.orderData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val location:String,
    val price:Double,
    val orderState: OrderState
)
