package com.wkq.tools.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.wkq.tools.R
import com.wkq.tools.databinding.ActivityMainBinding
import com.wkq.tools.databinding.ActivityVpDemoBinding
import com.wkq.tools.ui.adapter.VpFragmentAdapter
import com.wkq.tools.ui.fragment.DemoFragment
import com.wkq.tools.ui.fragment.DemoVpFragment
import com.wkq.tools.ui.fragment.FragmentTestActivity
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
 *@Time: 2025/2/27 16:31
 *
 *@Desc:
 */
class VpDemoActivity:AppCompatActivity() {

    val binding: ActivityVpDemoBinding by lazy {
        ActivityVpDemoBinding.inflate(LayoutInflater.from(this))    }
    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, VpDemoActivity::class.java))

        }
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val tableList: ArrayList<String> = ArrayList<String>()
        val fragmentList= ArrayList<Fragment>();
        fragmentList.add(DemoFragment.newInstance("1"))
        fragmentList.add(DemoFragment.newInstance("2"))
        fragmentList.add(DemoFragment.newInstance("3"))
        tableList.add("第一")
        tableList.add("第二")
        tableList.add("第三")

        binding.vpContent.offscreenPageLimit = tableList.size
        var adapter = VpFragmentAdapter(this, fragmentList)
        binding.vpContent.adapter = adapter
        setHideViewPager2Bar( binding.vpContent)
       val  commonNavigator = CommonNavigator(this)
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
//        binding.root.setHaveViewPager2Content(true)
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