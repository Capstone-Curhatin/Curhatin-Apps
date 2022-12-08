package com.capstone.core.ui.chat

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.core.databinding.ItemDoctorBinding
import com.capstone.core.domain.model.User
import com.capstone.core.utils.setImageUrl

class DoctorAdapter : RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }

    private val listDiffer = AsyncListDiffer(this, differCallback)
    var setData: List<User>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    private var listener: ((User) -> Unit)? = null
    fun setOnItemClick(listener: ((User) -> Unit)?){
        this.listener = listener
    }

    private var listenerDetail: ((User) -> Unit)? = null
    fun setOnDetailItemClick(listener: ((User) -> Unit)?){
        this.listenerDetail = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(setData[position], listener, listenerDetail)
    }

    override fun getItemCount(): Int = setData.size

    inner class ViewHolder(private val binding: ItemDoctorBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: User, listener: ((User) -> Unit)?, listenerDetail: ((User) -> Unit)?){
            with(binding){
                tvNamaDokter.text = data.name
                tvSpDokter.text = data.doctor?.specialist
                imgPicture.setImageUrl(data.picture.toString())

                tvExperience.text = data.doctor?.experience.toString()
//                tvReview.text = data.doctor?.reviews.toString()

                imgChat.setOnClickListener {
                    listener?.let { listener(data) }
                }
                rootView.setOnClickListener {
                    listenerDetail?.let { listenerDetail(data) }
                    //Log.d(TAG,"${data.id}")
                }

            }
        }
    }

}