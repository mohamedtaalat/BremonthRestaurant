package com.example.bremonthrestaurant.user.acessUser

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bremonthrestaurant.user.dataLogAndSign.User
import com.example.bremonthrestaurant.user.dataLogAndSign.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignViewModel(private val repo: UserRepo):ViewModel() {
    val emailLiveData:MutableLiveData<String>by lazy {
        MutableLiveData<String>()
    }
    fun checkEmail(email:String){
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            try {
               emailLiveData.postValue(repo.getEmail(email))
            }catch (ex:Exception){
                emailLiveData.postValue("null")
            }
        }
    }
    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.addUser(user)
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
}
class SignViewModelFactory(
    private val repo: UserRepo
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignViewModel(repo) as T
    }
}