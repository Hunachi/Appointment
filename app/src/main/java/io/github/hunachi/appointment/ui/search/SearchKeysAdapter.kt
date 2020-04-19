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
import io.github.hunachi.appointment.data.SearchKey
import kotlinx.android.synthetic.main.item_check.view.*

class SearchKeysAdapter : ListAdapter<SearchKey, SearchKeysAdapter.ViewHolder>(DIFFUtil) {

    // 選ばれているItemを通知するため。
    private val modifiableClicked: MutableLiveData<SearchKey> = MutableLiveData()
    val clickedItem: LiveData<SearchKey> = modifiableClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_check,
            parent,
            false
        ).let { ViewHolder(it) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(key: SearchKey) {
            if (key.isActive) {
                view.text_active.visibility = View.VISIBLE
                view.text_unactive.visibility = View.INVISIBLE
            } else {
                view.text_active.visibility = View.INVISIBLE
                view.text_unactive.visibility = View.VISIBLE
            }
            setupView(key)
        }

        fun setupView(key: SearchKey) {
            view.text_active.apply {
                text = key.key
                background
                setOnClickListener {
                    modifiableClicked.value = key
                }
            }
            view.text_unactive.apply {
                text = key.key
                background
                setOnClickListener {
                    modifiableClicked.value = key
                }
            }
        }
    }

    companion object {
        private val DIFFUtil = object : DiffUtil.ItemCallback<SearchKey>() {
            override fun areItemsTheSame(oldItem: SearchKey, newItem: SearchKey) =
                true

            override fun areContentsTheSame(
                oldItem: SearchKey,
                newItem: SearchKey
            ) = false
        }
    }
}
