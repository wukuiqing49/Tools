package com.wkq.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.wkq.ui.R
import com.wkq.ui.base.BaseRvAdapter
import com.wkq.ui.databinding.FragmentListBinding
import com.wkq.ui.view.EmptyView

/**
 *@Desc: 封装一个ListFragment
 *
 *@Author: wkq
 *
 *@Time: 2024/10/24 14:59
 *
 */
abstract class BaseListFragment<D,V:ViewBinding>: Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getData()
    }


    fun getEmptyView(): EmptyView {
        return binding.empty
    }

    fun getSmartRefreshLayout(): SmartRefreshLayout {

        return binding.smartRefresh
    }

    fun getRecycleView(): RecyclerView {
        return binding.rvContent
    }

    fun finishRefresh(){
        binding.smartRefresh.finishRefresh()
    }
    fun finishLoadMore(){
        binding.smartRefresh.finishLoadMore()
    }

    fun finishRefreshWithNoMoreData(){
        binding.smartRefresh.finishRefreshWithNoMoreData()
    }

    /**
     * 设置刷新头
     * @param header RefreshHeader
     */
    fun setRefreshHeader(header: RefreshHeader) {
        binding.smartRefresh.setRefreshHeader(header)
    }

    /**
     * 设置刷新尾部
     * @param footer RefreshFooter
     */
    fun setRefreshFooter(footer: RefreshFooter) {
        binding.smartRefresh.setRefreshFooter(footer)
    }

    /**
     * 是否开启刷新和加载更多
     * @param isOpen Boolean
     */
    fun setEnableRefreshAndLoadMore(isOpen: Boolean) {
        binding.smartRefresh.setEnableRefresh(isOpen)
        binding.smartRefresh.setEnableLoadMore(isOpen)
    }



    fun setEmpty(
        title: CharSequence, titleTextColor: Int, titleTextSize: Float, clickBackground: Int,
        marginTop: Int, listenerArgs: EmptyView.OnEmptyClickListener
    ) {
        binding.empty.setStyleImageAndTitle(
            title, titleTextColor, titleTextSize, clickBackground, marginTop, listenerArgs
        )
    }

    var mAdapter:BaseRvAdapter<D, V>?=null
    private fun initView() {

        setEmpty("暂无数据", R.color.design_default_color_error, 12f, R.mipmap.img_empty, 4,object :EmptyView.OnEmptyClickListener{
            override fun onEmptyClick(view: View) {
                emptyClick()
            }
        })

        binding.rvContent.layoutManager = getLayoutManager()
        mAdapter=setAdapter()
        binding.rvContent.adapter = mAdapter
        val header = MaterialHeader(requireActivity())
        header.setColorSchemeResources(
            R.color.color_smart_header_500, R.color.color_smart_header_500,
            R.color.color_smart_header_500, R.color.color_smart_header_500
        )
        binding.smartRefresh.setRefreshHeader(header)
        binding.smartRefresh.setRefreshFooter(ClassicsFooter(requireActivity()))
        binding.smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                binding.smartRefresh.setNoMoreData(false)
                onRefresh()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                onLoadMore()
            }

        })

    }

    /***
     * 空布局的展示和隐藏
     * @param isShow Boolean
     */
    fun showEmpty(isShow: Boolean) {
        if (isShow) {
            binding.rvContent.visibility = View.GONE
            binding.empty.visibility = View.VISIBLE
        } else {
            binding.rvContent.visibility = View.VISIBLE
            binding.empty.visibility = View.GONE
        }

    }

    /**
     * 设置是否有数据的状态
     * @param noMoreData Boolean
     */
    fun setNoMoreData(noMoreData: Boolean) {
        binding.smartRefresh.setNoMoreData(noMoreData)
    }

    /**
     * 完成刷新
     * @param noMoreData Boolean
     */
    fun finishLoadMore(noMoreData: Boolean) {
        if (noMoreData) {
            binding.smartRefresh.finishLoadMore(200, true, noMoreData)
        } else {
            binding.smartRefresh.finishRefresh()
            binding.smartRefresh.finishLoadMore()
        }
    }

    /**
     * 设置LayoutManager
     * @return RecyclerView.LayoutManager
     */
    abstract fun getLayoutManager(): RecyclerView.LayoutManager

    /**
     * 设置Adapter
     * @return RecyclerView.Adapter<RecyclerView.ViewHolder>
     */
    abstract fun setAdapter():  BaseRvAdapter<D, V>
     fun getAdapter():BaseRvAdapter<D, V>{
         if (mAdapter==null){mAdapter=setAdapter()}
       return mAdapter!!
    }

    /**
     * 刷新的回调
     */
    abstract fun onRefresh()

    /**
     * 加载更多的回调
     */
    abstract fun onLoadMore()

    abstract fun emptyClick()

    abstract fun getData()
}