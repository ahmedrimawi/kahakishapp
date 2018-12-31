package kahakish.shopping.com.kahakish

import android.graphics.Color
import android.view.WindowManager
import android.os.Build
import android.view.Window


class softKeyColor {

    lateinit var win: Window

     fun changeSoftKeyColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            win.setNavigationBarColor(Color.TRANSPARENT)
        }
    }
}