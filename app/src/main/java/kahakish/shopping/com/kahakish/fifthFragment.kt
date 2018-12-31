package kahakish.shopping.com.kahakish

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by arimawi on 4/24/18.
 */

class fifthFragment: Fragment(){


    companion object {
        fun newInstance(): Fragment
        {
            var fb : fifthFragment = fifthFragment()
            return fb
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_fifth,container,false)
    }
}

