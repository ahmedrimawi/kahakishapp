package kahakish.shopping.com.kahakish


import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kahakish.shopping.com.kahakish.Constants.Companion.URL_GET_BRANDS
import org.json.JSONException
import org.json.JSONObject


/**
 * Created by arimawi on 4/24/18.
 */

class secondFragment: Fragment(){

  //  val ipAddressLap = getLocalIpAddress()

//    private val brands_url = "http://192.168.1.89/kahakishApi/v1/?op="
//    val URL_GET_BRANDS = brands_url + "getbrands"

    lateinit var mBrandListView: ListView
    lateinit var mbrandlist: MutableList<brand>

    lateinit var pDialog:AlertDialog

    lateinit var customView: View

    companion object {
        fun newInstance(): Fragment
        {
            var fb : secondFragment = secondFragment()
            return fb
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        customView = inflater!!.inflate(R.layout.fragment_second,container,false)
        mBrandListView = customView.findViewById(R.id.brandListView) as ListView
        mbrandlist = mutableListOf<brand>()


        loadBrands()
         return customView

    }

    private fun displayLoader(){
        pDialog = ProgressDialog(this.activity)
        pDialog.setMessage("Logging In.. Please wait...")
        pDialog.setCancelable(false)
        pDialog.show()

    }

    private fun getLocalIpAddress(): String? {
        try {

            val wifiManager: WifiManager = context?.getSystemService(WIFI_SERVICE) as WifiManager
            return ipToString(wifiManager.connectionInfo.ipAddress)
        } catch (ex: Exception) {
            Log.e("IP Address", ex.toString())
        }

        return null
    }

    private fun ipToString(i: Int): String {
        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)

    }



    private fun loadBrands() {

        displayLoader()
        val stringRequest = StringRequest(Request.Method.GET, URL_GET_BRANDS, Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response)
                if (obj.getString("code").equals("200")) {
                    Thread.sleep(200)
                    pDialog.dismiss()
                    val array = obj.getJSONArray("brand")

                    for (i in 0..array.length() - 1) {
                        val objectBrands = array.getJSONObject(i)


                        val myBrandListIN = brand(
                                objectBrands.getString("brand_title"),
                                objectBrands.getString("brand_desc"),
                                objectBrands.getString("brand_img")
                        )

                        mbrandlist.add(myBrandListIN)
                        val adapter = BrandsListView(this.activity, mbrandlist)
                        mBrandListView.adapter = adapter
                    }
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

