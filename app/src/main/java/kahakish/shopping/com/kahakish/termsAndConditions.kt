package kahakish.shopping.com.kahakish

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class termsAndConditions : AppCompatActivity() {

    lateinit var backtonboarding: ImageView
    lateinit var termsTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)

        backtonboarding = findViewById(R.id.closeBtn)
        termsTitle = findViewById(R.id.termsTitle)
        termsTitle.setTypeface(termsTitle.getTypeface(), Typeface.BOLD)

        backtonboarding.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                goToOnBoarding()
            }

        })
    }

    // Tap on back tab at the top of the screen
    fun goToOnBoarding() {

        val intent = Intent(this, onBoarding::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }
}
