package kahakish.shopping.com.kahakish

import android.support.v4.app.FragmentActivity
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class BrandsListView(private val context: FragmentActivity?, internal var brand: List<brand>): ArrayAdapter<brand>(context, R.layout.brands_layout_views, brand) {

    lateinit var textViewTitle : TextView
    lateinit var textViewDescription : TextView
    lateinit var imageViewBrand : ImageView


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context?.layoutInflater
        val listViewItem = inflater?.inflate(R.layout.brands_layout_views, null,true)

        textViewTitle = listViewItem?.findViewById(R.id.brandTitle) as TextView
        textViewDescription = listViewItem?.findViewById(R.id.brandDesc) as TextView
        imageViewBrand = listViewItem?.findViewById(R.id.brandImage) as ImageView

        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,42F)

        val mybrand = brand[position]
        textViewTitle.text = mybrand.title
        textViewDescription.text = mybrand.description
        val imgURL = mybrand.image
        Picasso.with(context).load(imgURL).into(imageViewBrand)


        return listViewItem
    }


}