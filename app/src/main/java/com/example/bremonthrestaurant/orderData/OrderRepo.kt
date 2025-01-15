package com.example.bremonthrestaurant.orderData

class OrderRepo(private val dao: OrderDao) {
    suspend fun addOrder(order: Order)=dao.addOrder(order)
    suspend fun getOrder(id:Int):Order=dao.getOrder(id)
    suspend fun getAllOrders():List<Order> = dao.getAllOrders()
    suspend fun deleteOrder(id: Int)=dao.deleteOrder(id)
    suspend fun updateOrderState(orderState: OrderState,id: Int)=dao.updateOrderState(orderState, id)
}