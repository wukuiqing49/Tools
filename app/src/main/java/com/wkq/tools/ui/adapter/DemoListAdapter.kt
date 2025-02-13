package com.wkq.tools.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import com.wkq.tools.databinding.ItemRvBinding
import com.wkq.ui.base.BaseRvAdapter
import com.wkq.ui.base.BaseViewHolder


/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2024/10/24 17:21
 *
 */
class DemoListAdapter(context:Context):BaseRvAdapter<String, ItemRvBinding>(context) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ItemRvBinding> {
        return BaseViewHolder(ItemRvBinding.inflate(LayoutInflater.from(mContext),parent,false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ItemRvBinding>, position: Int) {
        holder.binding.tvContent.text=getBean(position)
    }
}