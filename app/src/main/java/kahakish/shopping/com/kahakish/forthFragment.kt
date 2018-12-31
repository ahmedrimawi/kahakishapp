package kahakish.shopping.com.kahakish


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by arimawi on 4/24/18.
 */

class forthFragment: Fragment(){


    companion object {
        fun newInstance(): Fragment
        {
            var fb : forthFragment = forthFragment()
            return fb
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_forth,container,false)
    }
}

