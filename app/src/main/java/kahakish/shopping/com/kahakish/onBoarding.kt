package kahakish.shopping.com.kahakish

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_on_boarding.*


class onBoarding : AppCompatActivity() {

    lateinit var dots: Array<ImageView>

    lateinit var pView: PageView
    var slider: IntArray = intArrayOf(R.layout.first_slide, R.layout.second_slide, R.layout.third_slide)

    lateinit var session: SessionHandler

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        session = SessionHandler(applicationContext)

        if (session.isLoggedIn()) {
            var i : Intent = Intent(applicationContext,home::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }
        else {
            pView = PageView(slider, this)
            sliderpager.adapter = pView

            createDots(0)
            sliderpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    createDots(position)

                    if (position == 2) {
                        forwardBtn.visibility = View.VISIBLE
                    } else {
                        forwardBtn.visibility = View.INVISIBLE
                    }
                }

            })


            signupBtn.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    goToRegisterScreen()
                }
            })

            signinBtn.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    goToLoginScreen()
                }
            })

            forwardBtn.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    goToHome()
                }
            })

        }


    }

    // Skip to Home screen
    fun goToTermsConditions(){
        val intent = Intent(this, termsAndConditions::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    // Skip to Home screen
    fun goToHome(){
        val intent = Intent(this, home::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    // After tapping on Sign up button
    fun goToRegisterScreen(){
        val intent = Intent(this, register::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    // After tapping on Sign in button
    fun goToLoginScreen(){
        val intent = Intent(this, login::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)

    }

    // After tapping on device back button
    override fun onBackPressed(){
        val alertBox = AlertDialog.Builder(this@onBoarding)
        alertBox.setTitle("Do you want to Exit ?")
        alertBox.setCancelable(true)
        alertBox.setPositiveButton("YES") { dialog, which ->
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
        }

        alertBox.setNegativeButton("NO") { dialog, which -> dialog.dismiss() }

        val alertDialog = alertBox.create()
        alertDialog.show()

    }

    // Show Active & InActive slide

    fun createDots(position: Int){
        if(SlideDots!=null){
            SlideDots.removeAllViews()
        }
        dots = Array(slider.size,{i->ImageView(this)})
        for(index in 0..slider.size - 1)
        {
            dots[index] = ImageView(this)
            if(index == position){
                dots[index].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots))
            }
            else{
                    dots[index].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots))
            }

            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(4,0,4,0)
            SlideDots.addView(dots[index], params)
        }
    }
}
