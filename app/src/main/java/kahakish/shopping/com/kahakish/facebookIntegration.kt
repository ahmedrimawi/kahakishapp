package kahakish.shopping.com.kahakish

import android.app.Application
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


class facebookIntegration: Application() {


    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }
}