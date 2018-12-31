package kahakish.shopping.com.kahakish

import android.content.Context
import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso

class CustomAdaptar : BaseAdapter {

    var con: Context? = null

    var itemsList: List<randomListItems>
    var arrayList: ArrayList<randomListItems>

    var inflator: LayoutInflater


    constructor(apps: ArrayList<randomListItems>, con: FragmentActivity?) : super() {
        this.itemsList = apps
        this.con = con
        arrayList = ArrayList<randomListItems>()
        arrayList.addAll(itemsList)
        inflator = con!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder? = ViewHolder()
        var rv: View? = convertView

        if (rv == null) {

            rv = inflator.inflate(R.layout.row_layout, parent, false)
            holder!!.tvTitle = rv.findViewById(R.id.itemTitle) as TextView
            holder!!.tvPrice = rv.findViewById(R.id.itemPrice) as TextView
            holder!!.iv = rv.findViewById(R.id.itemImage) as ImageView
            rv.setTag(holder)
        } else {
            holder = rv.getTag() as ViewHolder
        }

        holder.tvTitle.setText(itemsList.get(position).getItemTitle())
        holder.tvTitle.setTextColor(Color.parseColor("#ff000000"))
        holder.tvPrice.setText(itemsList.get(position).getPrice())
        holder.tvPrice.setTextColor(Color.RED)
        Picasso.with(con).load(itemsList.get(position).getImage()).into(holder.iv)
        rv!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //Toast.makeText(con,holder.tv.text.toString(),Toast.LENGTH_LONG).show()
            }
        })
        return rv
    }

    override fun getItem(position: Int): Any? {
        return null

    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return itemsList.size
    }

    class ViewHolder {
        lateinit var tvTitle: TextView
        lateinit var tvPrice: TextView
        lateinit var iv: ImageView
    }
}