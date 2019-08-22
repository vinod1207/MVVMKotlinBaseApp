package com.example.myapplication.baseUi

import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

/**
 * Created by Vinod Kumar on 9/8/19.
 */
open class BaseActivity : AppCompatActivity(){

    fun showProgressDialog(){
        indeterminateProgressDialog("This a progress dialog")
    }

    fun hideProgressDialog(){
        indeterminateProgressDialog("This a progress dialog").hide()
    }

    fun showShortToast(message:String){
        toast(message)
    }

    fun showLongToast(message: String) {
        longToast(message)
    }
}