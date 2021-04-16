package com.example.fuckingtests.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fuckingtests.databinding.UsersListItemBinding
import com.example.fuckingtests.models.User

class UsersAdapter: RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: UsersListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.idTv.text = user.id.toString()
            binding.nameTv.text = user.name
            binding.usernameTv.text = user.username
            binding.emailTv.text = user.email
        }
    }

    var users = listOf<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UsersListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user )
    }

    override fun getItemCount(): Int = users.size
}