package kahakish.shopping.com.kahakish

import ir.mirrajabi.searchdialog.core.Searchable



class searchModel(private var mTitle:String?):Searchable{


    override fun getTitle(): String {
       return mTitle!!
    }


}