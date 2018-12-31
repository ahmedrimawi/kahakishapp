package kahakish.shopping.com.kahakish

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by arimawi on 4/18/18.
 */

class PageView:PagerAdapter{

        var con:Context
        var layouts:IntArray
        var inflator:LayoutInflater

        constructor(layoutss: IntArray, conn: FragmentActivity?):super(){
            this.con = conn!!
            this.layouts = layoutss
            inflator = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        override fun isViewFromObject(view: View, `object`: Any): Boolean {

           return view == `object`
        }

        override fun getCount(): Int {

            return layouts.size
        }

        @SuppressLint("WrongViewCast")
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var view: View = inflator.inflate(layouts[position],container,false)
            container!!.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

            var view: View = `object` as View
            container!!.removeView(view)
        }

}