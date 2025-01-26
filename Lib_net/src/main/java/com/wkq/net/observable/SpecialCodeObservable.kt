package com.wkq.net.observable

import android.os.Handler
import android.os.Looper
import com.wkq.net.model.SpecialCodeBean
import java.util.Observable

/**
 *@Desc: 特定 code监听
 *
 *@Author: wkq
 *
 *@Time: 2025/1/14 10:47
 *
 */
class SpecialCodeObservable:Observable() {

    fun updateSpecialCode(specialCodeBean: SpecialCodeBean){
        Handler(Looper.getMainLooper()).post {
            setChanged()
            notifyObservers(specialCodeBean)
        }


    }

    companion object{
     private  var  instance :SpecialCodeObservable?=null ;

        fun newInstance():SpecialCodeObservable{
            if (instance==null){
                synchronized(SpecialCodeObservable::class){
                    if (instance==null)instance=SpecialCodeObservable()
                }
            }
            return  instance!!
        }
    }
}