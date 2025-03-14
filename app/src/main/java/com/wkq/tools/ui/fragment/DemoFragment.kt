package com.wkq.tools.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewbinding.ViewBinding
import com.wkq.common.ui.fragment.BaseListFragment
import com.wkq.tools.databinding.FragmentDemoBinding
import com.wkq.tools.databinding.ItemRvBinding
import com.wkq.tools.ui.adapter.DemoListAdapter
import com.wkq.ui.base.BaseRvAdapter
import com.wkq.ui.databinding.FragmentListBinding
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
class DemoFragment:Fragment () {
    private lateinit var binding: FragmentDemoBinding

    companion object{
        fun newInstance(content:String): DemoFragment{
            val args = Bundle()

            val fragment = DemoFragment()
            args.putString("content",content)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDemoBinding.inflate(LayoutInflater.from(activity),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val content= arguments?.let {
            it.getString("content")
        }
        binding.tvContent.text=content

    }

}