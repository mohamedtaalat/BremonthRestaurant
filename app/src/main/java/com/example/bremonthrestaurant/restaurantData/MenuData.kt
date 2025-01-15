package com.example.bremonthrestaurant.restaurantData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dataMenu")
data class MenuData(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val name:String,
    val price:Double,
    val description:String,
    val photo:String
)
