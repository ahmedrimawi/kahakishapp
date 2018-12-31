package kahakish.shopping.com.kahakish

import android.content.Context
import android.content.Context.SYSTEM_HEALTH_SERVICE
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import java.util.*
import javax.net.ssl.HostnameVerifier

class Constants {


    companion object {

//        val wm: WifiManager =
//        val ip: String = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress())
        const val base_url = "http://192.168.1.89/kahakishApi/v1/?op="

        const val URL_GET_USER = Constants.base_url + "getuser"
        const val URL_ADD_USER = Constants.base_url + "adduser"
        const val URL_GET_RANDITEM = Constants.base_url + "getrandomitems"
        const val URL_GET_BRANDS = Constants.base_url + "getbrands"
    }

}