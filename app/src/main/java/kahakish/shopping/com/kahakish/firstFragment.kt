package kahakish.shopping.com.kahakish

import android.app.AlertDialog
import android.app.ProgressDialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.widget.GridView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kahakish.shopping.com.kahakish.Constants.Companion.URL_GET_RANDITEM
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.fragment_one.*
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by arimawi on 4/24/18.
 */

class firstFragment: Fragment(){

    lateinit var homeViewPager: ViewPager

    lateinit var dots: Array<ImageView>

    var slider: IntArray = intArrayOf(R.layout.first_banner, R.layout.second_banner, R.layout.third_banner)

    lateinit var pView: PageView

    lateinit var pDialog:AlertDialog

    lateinit var productList: ArrayList<randomListItems>

    lateinit var madapter: PostAdapter

    lateinit var mDots: LinearLayout

    lateinit var firstfragmentlist: RecyclerView
    
    companion object {
        fun newInstance(): Fragment
        {
            var fb : firstFragment = firstFragment()
            return fb

        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val fragmentoneview = inflater!!.inflate(R.layout.fragment_one,container,false)
        homeViewPager = fragmentoneview.findViewById(R.id.pager)
        firstfragmentlist = fragmentoneview.findViewById(R.id.firstfragment_list)
        mDots = fragmentoneview.findViewById(R.id.SlideDot)
        pView = PageView(slider, activity)
        homeViewPager.adapter = pView

        createDots(0)
        homeViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
//                createDots(position)
//
//                if (position == 2) {
//                    forwardBtn.visibility = View.VISIBLE
//                } else {
//                    forwardBtn.visibility = View.INVISIBLE
//                }
            }

        })

        productList = ArrayList<randomListItems>()
        firstfragmentlist.hasFixedSize()
        firstfragmentlist.layoutManager = LinearLayoutManager(activity,OrientationHelper.HORIZONTAL, false)

        loadProducts()

        return fragmentoneview
    }

    fun createDots(position: Int){
        if(mDots!=null){
            mDots.removeAllViews()
        }
        dots = Array(slider.size,{i->ImageView(activity)})
        for(index in 0..slider.size - 1)
        {
            dots[index] = ImageView(activity)
            if(index == position){
                dots[index].setImageDrawable(activity?.let { ContextCompat.getDrawable(it,R.drawable.active_dots) })
            }
            else{
                dots[index].setImageDrawable(activity?.let { ContextCompat.getDrawable(it,R.drawable.inactive_dots) })
            }

            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(4,0,4,0)
            mDots.addView(dots[index], params)
        }
    }

    private fun displayLoader(){
        pDialog = ProgressDialog(this.activity)
        pDialog.setMessage("Logging In.. Please wait...")
        pDialog.setCancelable(false)
        pDialog.show()

    }

    private fun loadProducts() {

        displayLoader()

        val stringRequest = StringRequest(Request.Method.GET, URL_GET_RANDITEM, Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response)
                if (obj.getString("code").equals("200")) {
                    Thread.sleep(200)
                    pDialog.dismiss()
                    val array = obj.getJSONArray("products")

                    for (i in 0..array.length() - 1) {
                        val objectBrands = array.getJSONObject(i)

                        productList.add(randomListItems(objectBrands.getString("title"), objectBrands.getString("price"), objectBrands.getString("image")))

//                        cl = CustomAdaptar(itemArrayList, activity)
//                        gv.choiceMode = ListView.CHOICE_MODE_MULTIPLE
//                        gv.adapter = cl

                    }

                    madapter = PostAdapter(productList,activity)
                    firstfragment_list.adapter = madapter

                } else {
                    Thread.sleep(100)
                    pDialog.dismiss()
                    val alertBox = AlertDialog.Builder(this.activity)
                    alertBox.setTitle("Error !")
                    alertBox.setMessage(obj.getString("message"))
                    alertBox.setCancelable(true)
                    alertBox.setPositiveButton("OK") { dialog, which -> }
                    val alertDialog = alertBox.create()
                    alertDialog.show()
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }, Response.ErrorListener {
            Thread.sleep(100)
            pDialog.dismiss()
            val alertBox = AlertDialog.Builder(this.activity)
            alertBox.setTitle("Error !")
            alertBox.setMessage("No Internet Connection")
            alertBox.setCancelable(true)
            alertBox.setPositiveButton("OK") { dialog, which -> }
            val alertDialog = alertBox.create()
            alertDialog.show()
        })

        VolleySingleton.instance?.addRequestQueue<String>(stringRequest)

    }
}

