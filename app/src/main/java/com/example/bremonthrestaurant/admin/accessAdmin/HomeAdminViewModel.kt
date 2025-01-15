package com.example.bremonthrestaurant.admin.accessAdmin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bremonthrestaurant.menuData.MenuData
import com.example.bremonthrestaurant.menuData.MenuRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeAdminViewModel(private val repo: MenuRepo):ViewModel() {
    val itemsLiveData:MutableLiveData<List<MenuData>>by lazy {
        MutableLiveData<List<MenuData>>()
    }
    fun getAllItems(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                itemsLiveData.postValue(repo.selectAllItem())
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
    fun deleteItem(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.deleteItem(id)
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
    fun addItem(menuData: MenuData){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.addItem(menuData)
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
}
class HomeAdminViewModelFactory(
    private val repo: MenuRepo
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeAdminViewModel(repo) as T
    }
}