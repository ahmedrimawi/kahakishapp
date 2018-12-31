package kahakish.shopping.com.kahakish

import android.content.SharedPreferences
import android.content.Context
import android.content.Intent
import java.util.Date


public class SessionHandler {
    lateinit var mEditor: SharedPreferences.Editor
    lateinit var mPreferences: SharedPreferences
    lateinit var con: Context
    var PRIVATE_MOOD: Int = 0

    constructor(con:Context){
        this.con = con
        mPreferences = con.getSharedPreferences(PREF_NAME, PRIVATE_MOOD)
        mEditor = mPreferences.edit()
    }

    companion object {
        val PREF_NAME: String = "UserSession"
        val KEY_PASSWORD: String = "password"
        val KEY_EXPIRES: String = "expires"
        val KEY_EMAIL: String = "email"
        val KEY_EMPTY: String = ""
        val IS_LOGIN: String = "isLoggedIn"
    }

    fun createLoginSession(email: String, password: String){
        mEditor.putBoolean(IS_LOGIN, true)
        mEditor.putString(KEY_EMAIL, email)
        mEditor.putString(KEY_PASSWORD, password)
        mEditor.commit()
    }

    fun checkLoggin(){
        if (!this.isLoggedIn()){
            var newactivity: Intent = Intent(con, home::class.java)
            newactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            newactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            con.startActivity(newactivity)
        }
    }

    fun isLoggedIn(): Boolean {
       return mPreferences.getBoolean(IS_LOGIN, false)
    }

    fun getUserDetails(): HashMap<String, String>
    {
        var user: Map<String,String> = HashMap<String, String>()
        (user as HashMap).put(KEY_EMAIL, mPreferences.getString(KEY_EMAIL, null))
        (user as HashMap).put(KEY_PASSWORD, mPreferences.getString(KEY_PASSWORD, null))
        return user
    }

    fun loggedUser(){
        mEditor.clear()
        mEditor.commit()

        var newactivity: Intent = Intent(con, home::class.java)
        newactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        newactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        con.startActivity(newactivity)
    }
    
    



}