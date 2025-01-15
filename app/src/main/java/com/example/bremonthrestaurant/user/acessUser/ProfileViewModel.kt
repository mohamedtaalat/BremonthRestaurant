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

class ProfileViewModel(private val repo: UserRepo):ViewModel() {
    val passwordLiveData:MutableLiveData<String>by lazy {
        MutableLiveData<String>()
    }
    val nameLiveData:MutableLiveData<String>by lazy {
        MutableLiveData<String>()
    }
    val photoLiveData:MutableLiveData<String>by lazy {
        MutableLiveData<String>()
    }
    val userLiveData:MutableLiveData<User>by lazy {
        MutableLiveData<User>()
    }
    fun getUser(email:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userLiveData.postValue(repo.getUser(email))
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
    fun updateName(email: String,name:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.updateName(email, name)
                nameLiveData.postValue(name)
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
    fun updatePassword(email: String,password:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.updatePassword(email, password)
                passwordLiveData.postValue(password)
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
    fun updatePhoto(email: String,photo:String){
        viewModelScope.launch {
            try {
                repo.updatePhoto(email, photo)
                photoLiveData.postValue(photo)
            }catch (ex:Exception){
                Log.d("Eror", ex.message.toString())
            }
        }
    }
}
class ProfileViewModelFactory(
    private val repo: UserRepo
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repo) as T
    }
}