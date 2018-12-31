package kahakish.shopping.com.kahakish

class randomListItems {

    var img: String
    var itemTitleName: String
    var itemPrice: String


    constructor(itemTitle: String, price: String, itemImage: String): super() {
        this.img = itemImage
        this.itemTitleName = itemTitle
        this.itemPrice = price
    }

    fun getImage(): String{
        return img
    }

    fun getItemTitle(): String{
        return itemTitleName
    }

    fun getPrice(): String{
        return itemPrice
    }
}