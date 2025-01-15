package com.example.bremonthrestaurant.user.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bremonthrestaurant.restaurantData.MenuData
import com.example.bremonthrestaurant.restaurantData.MenuRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: MenuRepo):ViewModel() {
    val itemLiveData:MutableLiveData<MenuData>by lazy {
        MutableLiveData<MenuData>()
    }
    val itemsLiveData:MutableLiveData<List<MenuData>>by lazy {
        MutableLiveData<List<MenuData>>()
    }
    suspend fun selectItem(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                itemLiveData.postValue(repo.selectItem(id))
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
    suspend fun selectAllItems(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                itemsLiveData.postValue(repo.selectAllItem())
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
}
class HomeViewModelFactory(
    private val repo: MenuRepo
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}