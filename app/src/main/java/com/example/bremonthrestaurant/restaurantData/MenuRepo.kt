package com.example.bremonthrestaurant.restaurantData

class MenuRepo(private val dao: MenuDao) {
    suspend fun addItem(menuData: MenuData) = dao.addItem(menuData)
    suspend fun selectItem(id:Int):MenuData = dao.selectItem(id)
    suspend fun selectAllItem():List<MenuData> = dao.selectAllItems()
}