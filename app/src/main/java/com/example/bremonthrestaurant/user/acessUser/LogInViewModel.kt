package com.example.bremonthrestaurant.user.acessUser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bremonthrestaurant.user.dataLogAndSign.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogInViewModel(private val repo: UserRepo):ViewModel() {
    val emailLiveData:MutableLiveData<String>by lazy {
        MutableLiveData<String>()
    }
    val passwordLiveData:MutableLiveData<String>by lazy {
        MutableLiveData<String>()
    }
    fun getUser(email:String){
        viewModelScope.launch (Dispatchers.IO){
            try {
                emailLiveData.postValue(repo.getEmail(email))
                passwordLiveData.postValue(repo.getPassword(email))
            }catch (ex:Exception){
                emailLiveData.postValue("null")
                passwordLiveData.postValue("null")
            }
        }
    }
}
class LogInViewModelFactory(
    private val repo: UserRepo
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LogInViewModel(repo) as T
    }
}