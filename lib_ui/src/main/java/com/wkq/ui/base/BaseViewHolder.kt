package com.wkq.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<T : ViewBinding?>(var binding: T) : RecyclerView.ViewHolder(binding!!.root)