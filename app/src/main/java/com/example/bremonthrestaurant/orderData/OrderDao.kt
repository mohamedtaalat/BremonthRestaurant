package com.example.bremonthrestaurant.orderData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {
    @Insert
    fun addOrder(order: Order)
    @Query("select * from Orders ")
    fun getAllOrders():List<Order>
    @Query("select * from Orders where id=:id")
    fun getOrder(id:Int):Order
    @Query("Delete from Orders where id=:id")
    fun deleteOrder(id: Int)
    @Query("UPDATE Orders set orderState=:orderState where id =:id")
    fun updateOrderState(orderState: OrderState,id: Int)
}