package kahakish.shopping.com.kahakish

import java.util.Date
import kotlin.system.measureTimeMillis

/**
 * Created by Abhi on 20 Jan 2018 020.
 */

class User {
    lateinit var username: String
    lateinit var emailAddress: String
    lateinit var expiryDate: Date

    fun setUserName(name: String){
        this.username = name
    }

    fun setUserEmail(email: String){
        this.emailAddress = email
    }

    fun setSessionExpiryDate(sessionExpiryDate: Date){
        this.expiryDate = sessionExpiryDate
    }

    fun getUserName(): String{
        return username
    }

    fun getUserEmail(): String{
        return emailAddress
    }

    fun getSessionExpiryDate(): Date{
        return expiryDate
    }

}