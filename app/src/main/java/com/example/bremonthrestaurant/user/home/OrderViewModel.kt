package com.example.bremonthrestaurant.user.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bremonthrestaurant.orderData.Order
import com.example.bremonthrestaurant.orderData.OrderRepo
import com.example.bremonthrestaurant.orderData.OrderState
import com.example.bremonthrestaurant.restaurantData.MenuRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(private val repo: OrderRepo):ViewModel() {
    val orderLiveData:MutableLiveData<Order>by lazy {
        MutableLiveData<Order>()
    }
    fun getOrder(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                orderLiveData.postValue(repo.getOrder(id))
            }catch (ex:Exception){
                orderLiveData.postValue(null)
            }
        }
    }
    fun addOrder(order: Order){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.addOrder(order)
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
    fun cancelOrder(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.updateOrderState(OrderState.Canceled,id)
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
}
class OrderViewModelFactory(
    private val repo: OrderRepo
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderViewModel(repo) as T
    }
}