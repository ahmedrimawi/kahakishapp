package kahakish.shopping.com.kahakish

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.PixelFormat
import android.content.Intent
import kahakish.shopping.com.kahakish.splash
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.view.animation.Animation
import android.widget.ImageView


class SplashScreen : AppCompatActivity() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val window = window
        window.setFormat(PixelFormat.RGBA_8888)
    }

    var splashTread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        StartAnimations()
    }

    private fun StartAnimations() {
        var loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim)
        loadAnimation.reset()
        val linearLayout = findViewById<android.support.constraint.ConstraintLayout>(R.id.lin_lay)
        linearLayout.clearAnimation()
        linearLayout.startAnimation(loadAnimation)

        loadAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim)
        loadAnimation.reset()
        val imageView = findViewById<ImageView>(R.id.splash)
        imageView.clearAnimation()
        imageView.startAnimation(loadAnimation)

        splashTread = object : Thread() {
            override fun run() {
                try {
                    var waited = 0
                    while (waited < 3500) {
                        sleep(100)
                        waited += 100
                    }
                    val intent = Intent(this@SplashScreen,
                            onBoarding::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    this@SplashScreen.finish()
                } catch (e: InterruptedException) {

                } finally {
                    this@SplashScreen.finish()
                }

            }
        }
        splashTread.run {  }

    }
}
