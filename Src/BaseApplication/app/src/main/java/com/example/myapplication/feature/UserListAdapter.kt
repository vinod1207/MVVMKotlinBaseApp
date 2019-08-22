package com.example.myapplication.feature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

/**
 * Created by Vinod Kumar on 12/8/19.
 */
class UserListAdapter(private val userList: ArrayList<UserListModel>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: UserListModel) {
            val name = itemView.findViewById(R.id.name) as TextView
            val nickName = itemView.findViewById(R.id.nick_name) as TextView
            val emailAddress = itemView.findViewById(R.id.email) as TextView

            name.text = user.name
            nickName.text = user.username
            emailAddress.text = user.email
        }
    }
}