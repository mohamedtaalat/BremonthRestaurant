package com.example.bremonthrestaurant.user.dataLogAndSign

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User (
    @PrimaryKey
    val email:String,
    val name:String,
    val password:String,
    val photo:String

)
