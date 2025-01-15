package com.example.bremonthrestaurant.user.dataLogAndSign

class UserRepo(private val dao: UserDao) {
    suspend fun addUser(user: User)=dao.addUser(user)
    suspend fun getEmail(email:String):String =dao.selectUser(email).email
    suspend fun getPassword(email: String)=dao.selectUser(email).password
    suspend fun updateName(email: String,name:String)=dao.updateName(email,name)
    suspend fun updatePassword(email: String,password:String)=dao.updatePassword(email,password)
    suspend fun updatePhoto(email: String,photo:String)=dao.updatePhoto(email, photo)
    suspend fun getUser(email: String):User =dao.selectUser(email)
}