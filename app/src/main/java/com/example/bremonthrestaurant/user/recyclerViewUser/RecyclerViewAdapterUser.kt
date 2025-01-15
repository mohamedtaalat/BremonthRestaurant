package com.example.bremonthrestaurant.user.recyclerViewUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.bremonthrestaurant.R
import com.example.bremonthrestaurant.menuData.MenuData

class RecyclerViewAdapterUser(
    private var items:List<MenuData>,
    private val onClickButton:(position:Int)->Unit,
    private val onClickItem: (position: Int) -> Unit
    ):RecyclerView.Adapter<RecyclerViewAdapterUser.ViewHolderUser>() {
    inner class ViewHolderUser(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageItem:ImageView=itemView.findViewById(R.id.photoItem)
        val nameItem:TextView=itemView.findViewById(R.id.nameItem)
        val selectItem:Button=itemView.findViewById(R.id.selectItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUser {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_rec_user, parent, false)
        return ViewHolderUser(view)
    }
     fun setContent(newData:List<MenuData>){
        items=newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolderUser, position: Int) {
        holder.nameItem.text=items.get(position).name
        holder.imageItem.setImageURI(items.get(position).photo.toUri())
        holder.selectItem.setOnClickListener {
            onClickButton(position)
        }
        holder.itemView.setOnClickListener {
            onClickItem(position)
        }
    }

    override fun getItemCount(): Int =items.size
}