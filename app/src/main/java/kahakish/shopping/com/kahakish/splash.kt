package kahakish.shopping.com.kahakish

import android.animation.ObjectAnimator
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ImageView


class splash : AppCompatActivity() {


    lateinit var kahakish: TextView

    lateinit var progress: ProgressBar
    lateinit var loading: TextView

    lateinit var enbutton: Button
    lateinit var arbutton: Button

    var splashTread: Thread? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_splash)

        // Logo Animation
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        animation.reset()

        // Logo
        kahakish = findViewById(R.id.kahakishTxt)
        kahakish.clearAnimation()
        kahakish.startAnimation(animation)

        enbutton = findViewById(R.id.enbtn)
        arbutton = findViewById(R.id.arbtn)

        // Animation Function
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {

            }
            override fun onAnimationRepeat(arg0: Animation) {

            }
            override fun onAnimationEnd(arg0: Animation) {
                progress = findViewById(R.id.progressBar)
                loading = findViewById(R.id.loadingTxt)
                progress.visibility = View.VISIBLE

                loading.visibility = View.VISIBLE

                Handler().postDelayed({
                    goToNextScreen()
                   // progress.visibility = View.INVISIBLE
                 //   loading.visibility = View.INVISIBLE
                 //   doAnotherAnimation()
                }, 2000)

            }



        })

    }

    // Go to OnBoarding Screen
    fun goToNextScreen(){
        val intent = Intent(this, onBoarding::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun doAnotherAnimation(){

        var loadAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
        loadAnimation.reset()

        enbutton.visibility = View.VISIBLE
        arbutton.visibility = View.VISIBLE

        enbutton.clearAnimation()
        enbutton.startAnimation(loadAnimation)
        arbutton.clearAnimation()
        arbutton.startAnimation(loadAnimation)
        splashTread = object : Thread() {
                override fun run() {
                    try {
                        var waited = 0
                        while (waited < 3500) {
                            sleep(100)
                            waited += 100
                        }
                    val intent = Intent(this@splash,
                            splash::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    this@splash.finish()
                } catch (e: InterruptedException) {

                } finally {
                    this@splash.finish()
                }

            }
        }
        splashTread.run {  }
    }

}
