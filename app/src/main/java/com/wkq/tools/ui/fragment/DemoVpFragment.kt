package com.wkq.tools.ui.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
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
import androidx.viewpager2.widget.ViewPager2
import com.wkq.common.ui.fragment.BaseListFragment
import com.wkq.tools.databinding.FragmentDemoBinding
import com.wkq.tools.databinding.FragmentVpDemoBinding
import com.wkq.tools.databinding.ItemRvBinding
import com.wkq.tools.ui.adapter.DemoListAdapter
import com.wkq.tools.ui.adapter.VpFragmentAdapter
import com.wkq.tools.ui.adapter.VpInnerFragmentAdapter
import com.wkq.ui.base.BaseRvAdapter
import com.wkq.ui.databinding.FragmentListBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/2/13 11:39
 *
 *@Desc:
 */
class DemoVpFragment:Fragment () {
    private lateinit var binding: FragmentVpDemoBinding

    companion object{
        fun newInstance(content:String): DemoVpFragment{
            val args = Bundle()

            val fragment = DemoVpFragment()
            args.putString("content",content)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentVpDemoBinding.inflate(LayoutInflater.from(activity),container,false)
        return binding.root
    }
    fun setHideViewPager2Bar(vp:ViewPager2){
        try{
            val childAt = vp.getChildAt(0);
            if (childAt==null)return;
            if (childAt is RecyclerView){
                childAt.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }
        }catch (e:Exception ){

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tableList: ArrayList<String> = ArrayList<String>()
        val fragmentList= ArrayList<Fragment>();
        fragmentList.add(DemoFragment.newInstance("Fragment1"))
//        fragmentList.add(DemoVpInnerFragment.newInstance("Fragment2"))
        fragmentList.add(DemoFragment.newInstance("Fragment2"))
        fragmentList.add(DemoFragment.newInstance("Fragment3"))
        tableList.add("第一")
        tableList.add("第二")
        tableList.add("第三")
        setHideViewPager2Bar(binding.vpContent)
//        binding.vp2c.setHaveViewPager2Content(false)
        binding.vpContent.offscreenPageLimit = tableList.size
        var adapter = VpInnerFragmentAdapter(this, fragmentList)
        binding.vpContent.adapter = adapter

        val  commonNavigator = CommonNavigator(activity)
//        commonNavigator.isAdjustMode=true
        commonNavigator!!.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return tableList.size
            }

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {

                val badgePagerTitleView = BadgePagerTitleView(context)

                val simplePagerTitleView: SimplePagerTitleView =
                    ColorTransitionPagerTitleView(context)
                simplePagerTitleView.setText(tableList.get(index))
                simplePagerTitleView.normalColor = Color.parseColor("#662C2C3B")
                simplePagerTitleView.selectedColor = Color.parseColor("#FF2C2C3B")
                simplePagerTitleView.setTextSize(14f)
                simplePagerTitleView.setTypeface(null, Typeface.BOLD);
                simplePagerTitleView.setOnClickListener {
                    binding.vpContent.setCurrentItem(index)
                }

                badgePagerTitleView.innerPagerTitleView = simplePagerTitleView

                return badgePagerTitleView

            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.lineWidth = 36f
                indicator.lineHeight = 4f
                indicator.roundRadius = 2f
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.setColors(Color.parseColor("#FF2C2C3B"))
                indicator.yOffset = 6f
                return indicator
            }
        }
        binding.magicIndicator.navigator = commonNavigator

        binding.vpContent.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(

            ) {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.magicIndicator.onPageSelected(position)

            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                binding.magicIndicator.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                binding.magicIndicator.onPageScrolled(
                    position, positionOffset, positionOffsetPixels
                )
            }
        })



    }

}