package com.example.bremonthrestaurant.restaurantData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MenuDao {
    @Insert
    fun addItem(menuData: MenuData)
    @Query("select * from dataMenu where id=:id")
    fun selectItem(id:Int):MenuData
    @Query("select * from dataMenu")
    fun selectAllItems():List<MenuData>
}