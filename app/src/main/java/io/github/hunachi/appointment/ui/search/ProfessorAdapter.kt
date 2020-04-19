package io.github.hunachi.appointment.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.hunachi.appointment.R
import io.github.hunachi.appointment.data.Professor
import kotlinx.android.synthetic.main.item_professor_image.view.*

class ProfessorAdapter : ListAdapter<Professor, ProfessorAdapter.ViewHolder>(DIFFUtil) {

    // 選ばれているItemを通知するため。
    private val modifiableClicked: MutableLiveData<Professor> = MutableLiveData()
    val clickedItem: LiveData<Professor> = modifiableClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_professor_image,
            parent,
            false
        ).let { ViewHolder(it) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(checkItem: Professor) {
            view.btn_image.setOnClickListener {
                modifiableClicked.value = checkItem
            }
            Glide.with(view).load(checkItem.imageUrl).into(view.btn_image)
        }
    }

    companion object {
        private val DIFFUtil = object : DiffUtil.ItemCallback<Professor>() {
            override fun areItemsTheSame(oldItem: Professor, newItem: Professor) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: Professor,
                newItem: Professor
            ) = oldItem == newItem
        }
    }
}
