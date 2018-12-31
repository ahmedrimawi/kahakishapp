package kahakish.shopping.com.kahakish


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
//import android.support.v4.widget.SearchViewCompat
import android.support.v7.widget.SearchView
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.android.synthetic.main.activity_home.*


class home : AppCompatActivity() {


    lateinit var bnav: BottomNavigationView
    lateinit var screenTitle: TextView

    lateinit var searchBtn: ImageButton

    var searchView: SearchView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(mtoolbar)

        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
        bnav = findViewById<BottomNavigationView>(R.id.navigation)

        bnav.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                if (!isConnected()) run {
                    val alertBox = AlertDialog.Builder(this@home)
                    alertBox.setTitle("No Internet Connection!")
                    alertBox.setCancelable(true)
                    alertBox.setPositiveButton("OK") { dialog, which -> }
                    val alertDialog = alertBox.create()
                    alertDialog.window.setLayout(200, 100)
                    alertDialog.show()

                }
            }
        })


            screenTitle = findViewById<TextView>(R.id.navigationTitle)


        /*       searchBtn = findViewById<ImageButton>(R.id.search_btn)
             searchBtn.setOnClickListener {
                  SimpleSearchDialogCompat(this@home,"Search", "What Are You Looking For ...?",null,
                             initData(), SearchResultListener { baseSearchDialogCompat, item, i ->

                              goToSearchResult()
                              baseSearchDialogCompat.dismiss()

                     }).show()*/
        screenTitle.textSize = resources.getDimension(R.dimen.textsize)

        var ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, firstFragment.newInstance())
        ft.commit()

        screenTitle.text = "Home"
        screenTitleSize()


        bnav.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                var selectedFragment: Fragment? = null
                when(item.itemId){

                    R.id.tap1 -> selectedFragment = firstFragment.newInstance()

                    R.id.tap2 -> selectedFragment = secondFragment.newInstance()
                    R.id.tap3 -> selectedFragment = thirdFragment.newInstance()
                    R.id.tap4 -> selectedFragment = forthFragment.newInstance()
                    R.id.tap5 -> selectedFragment = fifthFragment.newInstance()


                }

                screenTitle.text = item.title
                screenTitleSize()

                var ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                ft.replace(R.id.frame_layout, selectedFragment)

                ft.commit()
                return true


            }

        })

        }

    private fun initData(): ArrayList<searchModel> {

        val items = ArrayList<searchModel>()
        items.add(0,searchModel("T-Shirt"))
        items.add(1,searchModel("Shoes"))
        items.add(2,searchModel("Trouser"))
        items.add(3,searchModel("Sun Glasses"))
        items.add(4,searchModel("Bages"))

        return items
    }

    fun screenTitleSize() : Float {
        screenTitle.textSize = resources.getDimension(R.dimen.textsize)
        return screenTitle.textSize
    }

    fun goToSearchResult(){
        val intent = Intent(this, searchResult::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun isConnected(): Boolean {
        val cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }




}

