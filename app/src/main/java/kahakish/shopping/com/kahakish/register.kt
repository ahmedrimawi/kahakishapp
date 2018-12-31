package kahakish.shopping.com.kahakish


import android.app.AlertDialog
import android.app.ProgressDialog
import android.app.VoiceInteractor
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*

import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest

import org.json.JSONException
import org.json.JSONObject

import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import kahakish.shopping.com.kahakish.Constants.Companion.URL_ADD_USER


class register : AppCompatActivity() {

    lateinit var backtonboarding: ImageView
    lateinit var name: EditText
    lateinit var emailAddress: EditText
    lateinit var password: EditText
    lateinit var confirmpass: EditText
    lateinit var signup: Button
    lateinit var emailMsg : String
    lateinit var passMsg : String
    lateinit var confirmMsg : String
    lateinit var nameMsg : String
    lateinit var request: JSONObject
    lateinit var jsArrayRequest: JsonObjectRequest
    lateinit var pDialog:AlertDialog

    private val KEY_STATUS = "status"
    private val KEY_MESSAGE = "message"
    private val KEY_EMAIL = "email"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"
    private val KEY_PASSWORD_CONFIRM = "password confirm"
    private val KEY_EMPTY = ""

//    private val register_url = "http://192.168.1.212/kahakishApi/v1/?op="
//    val URL_ADD_ARTIST = register_url + "adduser"
    private var session: SessionHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        session = SessionHandler(applicationContext)
        setContentView(R.layout.activity_register)


        backtonboarding = findViewById(R.id.closeBtn)
        name = findViewById(R.id.nameT)
        emailAddress = findViewById(R.id.emailT)
        password = findViewById(R.id.passT)
        confirmpass = findViewById(R.id.confirmPassT)
        signup = findViewById(R.id.signupBtn)
        val termsandcond = findViewById(R.id.condition) as TextView
         backtonboarding.setOnClickListener(object : View.OnClickListener{
             override fun onClick(v: View?) {
                 goToOnBoarding()
             }

         })

        termsandcond.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                goToTermsConditions()
            }

        })

        signup.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                intialize()

                if (!isConnected()) run {
                    val alertBox = AlertDialog.Builder(this@register)
                    alertBox.setTitle("No Internet Connection!")
                    alertBox.setCancelable(true)
                    alertBox.setPositiveButton("OK") { dialog, which -> }
                    val alertDialog = alertBox.create()
                    alertDialog.show()
                }

                else if(!validate()){
                    validate()
                }

                else{
                    registerUser()
                    //goToHome()
                }
            }

        })

    }

    private fun registerUser() {
        //getting the record values
        val username = name?.text.toString()
        val email = emailAddress?.text.toString()
        val pass = password?.text.toString()
        val conPass = confirmpass?.text.toString()

        displayLoader()
        val stringRequest = object : StringRequest(Request.Method.POST, URL_ADD_USER, Response.Listener<String> {response ->
            try {
                val obj = JSONObject(response)
                if (obj.getString("code").equals("200")){
                    Thread.sleep(200)
                    pDialog.dismiss()
                    goToHome()
                }
                else {
                    Thread.sleep(100)
                    pDialog.dismiss()
                    val alertBox = AlertDialog.Builder(this@register)
                    alertBox.setTitle("Error !")
                    alertBox.setMessage(obj.getString("message"))
                    alertBox.setCancelable(true)
                    alertBox.setPositiveButton("OK") { dialog, which -> }
                    val alertDialog = alertBox.create()
                    alertDialog.show()
                }

            }catch (e: JSONException)
            {
                e.printStackTrace()
            }

        },
                object : Response.ErrorListener {
                    override fun onErrorResponse(volleyError: VolleyError) {
                        Thread.sleep(100)
                        pDialog.dismiss()
                        val alertBox = AlertDialog.Builder(this@register)
                        alertBox.setTitle("Error !")
                        alertBox.setMessage("No Internet Connection")
                        alertBox.setCancelable(true)
                        alertBox.setPositiveButton("OK") { dialog, which -> }
                        val alertDialog = alertBox.create()
                        alertDialog.show()
                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("name", username)
                params.put("email address", email)
                params.put("password", pass)
                params.put("confirm password", conPass)
                return params
            }
        }

        //Add request to queue
        VolleySingleton.instance?.addRequestQueue(stringRequest)

    }

    fun validate(): Boolean {
        var valid = true

        if (emailMsg.isEmpty()) {
            emailAddress.setError("Email is required")
            valid = false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailMsg).matches()) {
            emailAddress.setError("Invalid Email")
            valid = false
        }
        if (passMsg.isEmpty()) {
            password.setError("Password is required")
            valid = false
        }

        if (confirmMsg.isEmpty()) {
            confirmpass.setError("Confirm Password is required")
            valid = false
        }
        if (passMsg.length > 50) {
            password.setError("Password should be less than 50 chars")
            valid = false
        }
        if (passMsg.length < 3) {
            password.setError("Password should be more than 2 chars")
            valid = false
        }
        if (password.getText().toString() != confirmpass.getText().toString()) {
            Toast.makeText(this, "Password and confoirm password should be the same", Toast.LENGTH_SHORT).show();
            valid = false
        }

        return valid
    }
    // Tap on back tab at the top of the screen
    fun goToOnBoarding() {

        val intent = Intent(this, onBoarding::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun goToTermsConditions(){
        val intent = Intent(this, termsAndConditions::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun isConnected(): Boolean {
        val cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun goToHome() {
        val intent = Intent(this, home::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }



    // Tap on device back button
    override fun onBackPressed() {
        val onBoarding = Intent(this, onBoarding::class.java)
        startActivity(onBoarding)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun displayLoader() {

        pDialog = ProgressDialog(this@register)
        pDialog.setMessage("Logging In.. Please wait...")
        pDialog.setCancelable(false)
        pDialog.show()

    }

    fun intialize() {
            nameMsg = name.getText().toString().trim({ it <= ' ' })
            emailMsg = emailAddress.text.toString().trim { it <= ' ' }
            passMsg = password.getText().toString().trim({ it <= ' ' })
            confirmMsg = confirmpass.getText().toString().trim({ it <= ' ' })
        }
}
