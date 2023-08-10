package com.example.api_mark_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private var userList: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val userName : TextView = itemView.findViewById(R.id.textName)
        val userSurname: TextView = itemView.findViewById(R.id.textSurname)
        val userPlanId : TextView = itemView.findViewById(R.id.textPlanID)
        val userPlanAmount : TextView = itemView.findViewById(R.id.textAmount)
        val bgColor :CardView = itemView.findViewById(R.id.userCardItemView)

    }

    fun setFilteredist(userList: ArrayList<User>){
        this.userList = userList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.userName.text = "NAME: ${currentItem.Name}"
        holder.userSurname.text = "SURNAME: ${currentItem.Surname}"
        holder.userPlanId.text = "PLAN ID: ${currentItem.PlanID}"
        holder.userPlanAmount.text = "AMOUNT: R${currentItem.Amount}"

        val context = holder.itemView.context

        when(currentItem.Amount){
            in 0..100 -> holder.bgColor.setCardBackgroundColor(context.getColor(R.color.bronze))
            in 101..200 -> holder.bgColor.setCardBackgroundColor(context.getColor(R.color.silver))
            in 201..401 -> holder.bgColor.setCardBackgroundColor(context.getColor(R.color.gold))
            else ->{
                holder.bgColor.setCardBackgroundColor(context.getColor(R.color.white))
            }
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}