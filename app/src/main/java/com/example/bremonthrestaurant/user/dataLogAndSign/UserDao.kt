package com.example.bremonthrestaurant.user.dataLogAndSign

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun addUser(user: User)
    @Query("select * from User where email =:Email")
    fun selectUser(Email:String):User
    @Query("UPDATE user set password =:password where email=:email")
    fun updatePassword(email:String,password:String)
    @Query("UPDATE user set photo=:photo where email=:email")
    fun updatePhoto(email: String,photo:String)
    @Query("UPDATE user set name=:name where email=:email")
    fun updateName(email: String,name:String)
}