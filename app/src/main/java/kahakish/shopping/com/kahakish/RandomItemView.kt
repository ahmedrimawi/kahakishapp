package kahakish.shopping.com.kahakish

import android.app.Activity
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class RandomItemView(private val context: Activity, internal var rand: List<item>): ArrayAdapter<item>(context, R.layout.row_layout, rand) {

    lateinit var textViewTitle : TextView
    lateinit var textViewPrice : TextView
    lateinit var imageViewBrand : ImageView


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.brands_layout_views, null,true)

        textViewTitle = listViewItem.findViewById(R.id.itemTitle) as TextView
        textViewPrice = listViewItem.findViewById(R.id.itemPrice) as TextView
        imageViewBrand = listViewItem.findViewById(R.id.itemImage) as ImageView

        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,42F)

        val myItem = rand[position]
        textViewTitle.text = myItem.title
        textViewPrice.text = myItem.price
        val imgURL = myItem.image
        Picasso.with(context).load(imgURL).into(imageViewBrand)


        return listViewItem
    }


}