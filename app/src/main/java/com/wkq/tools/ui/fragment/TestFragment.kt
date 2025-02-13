package com.wkq.tools.ui.fragment

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewbinding.ViewBinding
import com.wkq.common.ui.fragment.BaseListFragment
import com.wkq.tools.databinding.ItemRvBinding
import com.wkq.tools.ui.adapter.DemoListAdapter
import com.wkq.ui.base.BaseRvAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/2/13 11:39
 *
 *@Desc:
 */
class TestFragment:BaseListFragment<String, ItemRvBinding> () {
    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }

    override fun setAdapter(): BaseRvAdapter<String, ItemRvBinding> {
        return  DemoListAdapter(requireActivity())
    }

    override fun onRefresh() {
       lifecycleScope.launch {
           delay(3000)

           val newData=ArrayList<String>()
           newData.add("新数据1")
           newData.add("新数据2")
           newData.add("新数据3")
           newData.add("新数据4")
           newData.add("新数据5")
           newData.add("新数据6")
           newData.add("新数据7")
           newData.add("新数据8")
           newData.add("新数据9")
           newData.add("新数据10")
           getAdapter().setNewData(newData)
       }

        finishRefresh()
    }

    override fun onLoadMore() {
        getAdapter().addItem("添加数据")
        finishLoadMore()
    }

    override fun emptyClick() {

    }

    override fun getData() {
        getAdapter().addItem("设置数据")
        getAdapter().addItem("设置数据")
        getAdapter().addItem("设置数据")
        getAdapter().addItem("设置数据")
        getAdapter().addItem("设置数据")
        getAdapter().addItem("设置数据")
        getAdapter().addItem("设置数据")
        getAdapter().addItem("设置数据")
        finishRefresh()
        finishLoadMore()
    }
}