package com.example.greeenpacket_test.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greeenpacket_test.R
import com.example.greeenpacket_test.databinding.ViewUserBinding
import com.example.greeenpacket_test.models.User
import com.example.greeenpacket_test.views.UserListFragmentDirections
import kotlinx.android.synthetic.main.view_user.view.*

/**
 * a class to handle [RecyclerView] adapter
 */
class UsersRecyclerViewAdapter() :
    ListAdapter<User, UsersRecyclerViewAdapter.UsersViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.view_user, parent, false
            )
        )
    }

    /**
     * assign value and event on view on given position
     */
    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        getItem(position).let { user ->
            with(holder) {
                val transitionName = user.userId ?: ""
                ViewCompat.setTransitionName(holder.avatarView, transitionName)
                bind(user)

            }

        }
    }


    class UsersViewHolder(private val binding: ViewUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context: Context = binding.root.context
        val avatarView: ImageView = binding.root.imageView_avatar
        private val nameView: TextView = binding.root.textView_name
        private val ageView: TextView = binding.root.textView_age

        fun bind(value: User) {
            ViewCompat.setTransitionName(avatarView, "image_${value.userId}")
            ViewCompat.setTransitionName(nameView, "name_${value.userId}")
            ViewCompat.setTransitionName(ageView, "age_${value.userId}")
            with(binding) {
                this.user = value
                this.clickListener = View.OnClickListener {
                    val action =
                        UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(
                            user!!
                        )
                    itemView.findNavController()
                        .navigate(
                            action,
                            FragmentNavigatorExtras(
                                avatarView to ViewCompat.getTransitionName(
                                    avatarView
                                )!!,
                                nameView to ViewCompat.getTransitionName(nameView)!!,
                                ageView to ViewCompat.getTransitionName(ageView)!!
                            )
                        )
                }
                executePendingBindings()
            }
        }
    }
}

private class UserDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.userId == newItem.userId
                && oldItem.employeeCode.toString() == oldItem.employeeCode.toString()
                && oldItem.duties.toString() == oldItem.duties.toString()
    }
}