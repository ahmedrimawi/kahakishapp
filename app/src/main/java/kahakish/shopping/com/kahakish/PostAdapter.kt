package kahakish.shopping.com.kahakish

import android.content.Context
import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class PostAdapter: RecyclerView.Adapter<PostAdapter.ProductViewHolder> {

    var itemsList: List<randomListItems>
    var con: Context? = null

    constructor(apps: List<randomListItems>, con: FragmentActivity?) : super() {
        this.itemsList = apps
        this.con = con
    }


    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemTitle)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemImg: ImageView = itemView.findViewById(R.id.itemImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ProductViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount()= itemsList.size

    override fun onBindViewHolder(holder: PostAdapter.ProductViewHolder?, position: Int) {
        holder!!.itemName.text = itemsList.get(position).getItemTitle()
        holder!!.itemName.setTextColor(Color.parseColor("#ff000000"))
        holder!!.itemPrice.text = itemsList.get(position).getPrice()
        holder!!.itemPrice.setTextColor(Color.GRAY)
        Picasso.with(con).load(itemsList.get(position).getImage()).into(holder.itemImg)
    }
}