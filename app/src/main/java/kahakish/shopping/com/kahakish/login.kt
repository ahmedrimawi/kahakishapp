package kahakish.shopping.com.kahakish

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.support.v7.widget.AppCompatCheckBox
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.widget.*
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.EditText
import android.graphics.Typeface
import android.os.Handler
import android.text.Editable
import android.webkit.WebView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ravikoradiya.library.CenterTitle
import kahakish.shopping.com.kahakish.Constants.Companion.URL_GET_USER
import org.json.JSONException
import org.json.JSONObject


class   login : AppCompatActivity() {
    lateinit var backtonboarding: ImageView
    lateinit var emailAddress: EditText
    lateinit var password: EditText
    lateinit var emailMsg : String
    lateinit var passMsg : String
    lateinit var signin: Button
    lateinit var resetPassword: EditText


    lateinit var passCheck:CheckBox

    lateinit var forgetPass: TextView

    lateinit var loginSpinner: ProgressBar

    lateinit var request: JSONObject
    lateinit var jsArrayRequest: JsonObjectRequest
    lateinit var pDialog:AlertDialog
    lateinit var session: SessionHandler



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = SessionHandler(applicationContext)

        backtonboarding = findViewById(R.id.closeBtn)
        emailAddress = findViewById(R.id.emailT)
        password = findViewById(R.id.passT)
        passCheck = findViewById(R.id.showPass)

        signin = findViewById(R.id.signinBtn)

        forgetPass = findViewById(R.id.forgetPass)

        loginSpinner = findViewById(R.id.progressBar1)

        resetPassword = findViewById(R.id.resetPasswordT)
        backtonboarding.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                goToOnBoarding()
            }

        })

        passCheck.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            if (!isChecked) {
                // show password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance())
            } else {
                // hide password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            }
        })


        signin.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                intialize()

                if (!isConnected()) run {
                    val alertBox = AlertDialog.Builder(this@login)
                    alertBox.setTitle("No Internet Connection!")
                    alertBox.setCancelable(true)
                    alertBox.setPositiveButton("OK") { dialog, which -> }
                    val alertDialog = alertBox.create()
                    alertDialog.window.setLayout(200,100)
                    alertDialog.show()

                }

                else if(!validate()){

                }

                else{

                        loginFunctionality()
                        pDialog.dismiss()


                }
            }

        })

        forgetPass.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {


                    val alertBox = AlertDialog.Builder(this@login)
                    val title = TextView(this@login)
                    title.setText("Forget Password")
                    title.setPadding(10, 15, 10, 15)
                    title.gravity = Gravity.CENTER
                    title.textSize = 20F
                    title.setTextColor(-0x1000000)
                    title.typeface = Typeface.DEFAULT_BOLD
                    alertBox.setCustomTitle(title)
                    alertBox.setMessage("To reset password, Please enter your email, then press on 'Send' button and check email to follow instruction.\n")
                    val resetEmail = EditText(this@login)
                    alertBox.setView(resetEmail)
                    resetEmail.hint = "Enter your email address"
                    alertBox.setPositiveButton("Send") { dialog, which -> }

                    alertBox.setNegativeButton("Cancel") { dialog, which ->
                        dialog.cancel() }
                    val alertDialog = alertBox.create()
                    alertDialog.show()

                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener({

                        if (resetEmail.text.isEmpty()) {
                            resetEmail.setError("Please enter email address")
                        }
                        else if (!Patterns.EMAIL_ADDRESS.matcher(resetEmail.text).matches()){
                            resetEmail.setError("Invalid Email")
                        }
                        else{
                           loginSpinner.visibility = View.VISIBLE
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).isEnabled = false
                            resetEmail.isEnabled = false
                            Handler().postDelayed({
                                loginSpinner.visibility = View.INVISIBLE
                                alertDialog.dismiss()
                                sucessMsgDialog()

                            },1000)


                        }
                    })

                   val msgAlignemnt = alertDialog.findViewById<TextView>(android.R.id.message)
                    msgAlignemnt.gravity = Gravity.CENTER
            }

        })
    }

    fun validate(): Boolean {
        var valid = true

        if (emailAddress.getText().isEmpty()) {
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
        return valid
    }


    // Tap on back tab at the top of the screen
    fun goToOnBoarding() {

        val intent = Intent(this, onBoarding::class.java)
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    fun sucessMsgDialog(){
        val SuccessMsg = AlertDialog.Builder(this@login)
        val title = TextView(this@login)
        title.setText("Forget Password")
        title.setPadding(10, 15, 10, 15)
        title.gravity = Gravity.CENTER
        title.textSize = 20F
        title.setTextColor(-0x1000000)
        title.typeface = Typeface.DEFAULT_BOLD
        SuccessMsg.setCustomTitle(title)
        SuccessMsg.setMessage("Sent Successfully, check your inbox")
        SuccessMsg.setCancelable(true)
        SuccessMsg.setPositiveButton("OK") { dialog, which -> }
        val alertDialog = SuccessMsg.create()
        alertDialog.show()
    }

    fun intialize() {
        emailMsg = emailAddress.text.toString().trim { it <= ' ' }
        passMsg = password.getText().toString().trim({ it <= ' ' })
    }

    private fun displayLoader() {
        pDialog = ProgressDialog(this@login)
        pDialog.setMessage("Logging In.. Please wait...")
        pDialog.setCancelable(false)
        pDialog.show()

    }

    private fun loginFunctionality() {

        val email = emailAddress?.text.toString()
        val pass = password?.text.toString()

        displayLoader()
        val stringRequest = object : StringRequest(Request.Method.POST, URL_GET_USER, Response.Listener<String> {response ->
            try {
                val obj = JSONObject(response)
                if (obj.getString("code").equals("200")){
                    Thread.sleep(200)
                    pDialog.dismiss()
                    session.createLoginSession(email, pass)
                    goToHome()
                }
                else {
                    Thread.sleep(100)
                    pDialog.dismiss()
                    val alertBox = AlertDialog.Builder(this@login)
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
                        val alertBox = AlertDialog.Builder(this@login)
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
                params.put("email address", email)
                params.put("password", pass)
                return params
            }
        }


        VolleySingleton.instance?.addRequestQueue(stringRequest)
    }


}